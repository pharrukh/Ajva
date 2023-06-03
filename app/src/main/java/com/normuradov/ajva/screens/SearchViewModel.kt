package com.normuradov.ajva.ui.theme

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.michaeltroger.latintocyrillic.Alphabet
import com.michaeltroger.latintocyrillic.LatinCyrillicFactory
import com.normuradov.ajva.DictionaryApplication
import com.normuradov.ajva.data.Word
import com.normuradov.ajva.data.WordRepository
import com.normuradov.ajva.utils.transliterate
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private val TAG = "SEARCH_VIEW_MODEL"

class SearchViewModel(
    private val wordRepository: WordRepository,
) : ViewModel() {
    private val _rawUserInput = MutableSharedFlow<String>(replay = 0)
    private val _uiState = MutableStateFlow(SearchScreenUiState())
    val uiState: StateFlow<SearchScreenUiState> = _uiState

    init {
        viewModelScope.launch {
            _rawUserInput
                .debounce(500)
                .collect { debouncedInput ->
                    _uiState.update { _uiState.value.copy(userSearchText = debouncedInput) }
                    searchForWordsByUserInput()
                    _uiState.update { _uiState.value.copy(isLoading = false) }
                }
        }
    }

    fun updateText(userInput: String) {
        _uiState.update { _uiState.value.copy(isLoading = true) }
        viewModelScope.launch {
            _rawUserInput.emit(userInput)
        }
    }

    fun showLoader() {
        _uiState.update { _uiState.value.copy(isLoading = true) }
    }

    fun searchForWordsByOCR(recognizedPossibleWords: List<String>) {
        val topFive = recognizedPossibleWords
        viewModelScope.launch {
            val result = mutableSetOf<Word>()
            for (recognizedWord in topFive) {
                val words = wordRepository.search(recognizedWord)
                if(words.isNotEmpty())
                    result.add(words[0])
            }
            _uiState.update { _uiState.value.copy(foundWords = result.toList(), isLoading = false) }
        }
    }


    fun process(text: String) {
        // Remove all non alphanumeric characters from Latin and Cyrillic
        val sanitizedText = text.replace("[^A-Za-zА-Яа-я0-9 ]".toRegex(), "")
        viewModelScope.launch {
            var transliteratedText = sanitizedText
            val latinCyrillic = LatinCyrillicFactory.create(Alphabet.RussianIso9)
            val isCyrillic = latinCyrillic.isCyrillic(sanitizedText)
            Log.v(TAG, "isCyrillic: $isCyrillic")
            if (!isCyrillic) {
                val cyrillic = transliterate(sanitizedText.lowercase())
                transliteratedText = cyrillic
            }
            Log.v(TAG, "transliteratedText: $transliteratedText")
            val words = split(transliteratedText)
            Log.v(TAG, "words: $words")
            searchForWordsByOCR(words)
        }
    }

    private fun split(text: String): List<String> {
        val words = text.replace("\n", " ").split(" ").filter { it -> it.length > 4 }
        Log.v(TAG, words.joinToString { "$it, " })
        return words
    }

    fun searchForWordsByUserInput() {
        if (_uiState.value.userSearchText.isEmpty()) {
            _uiState.update { _uiState.value.copy(foundWords = listOf()) }
            return
        }

        viewModelScope.launch {
            val query = _uiState.value.userSearchText
            Log.v("SEARCH", query)
            // Sanitize query by keeing only Cyrrilic letters
            val sanitizedQuery = query.replace(Regex("[^а-яА-Я]"), "")
            val words = wordRepository.search(sanitizedQuery)
            Log.v("SEARCH", "found ${words.size} words")
            _uiState.update { _uiState.value.copy(foundWords = words) }
            Log.v("STATE_UPDATE", "Now there are ${_uiState.value.foundWords.size} words")
        }
    }

    fun navigateToSearchScreen() {
        _uiState.update { _uiState.value.copy(selectedWord = null, mode = SearchViewMode.Search) }
    }

    fun navigateToWord(word: Word) {
        _uiState.update { _uiState.value.copy(selectedWord = word, mode = SearchViewMode.Detail) }
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as DictionaryApplication)
                SearchViewModel(wordRepository = application.container.wordRepository)
            }
        }
    }
}

enum class SearchViewMode {
    Search, Detail
}

data class SearchScreenUiState(
    val mode: SearchViewMode = SearchViewMode.Search,
    val isLoading: Boolean = false,
    val selectedWord: Word? = null,
    val foundWords: List<Word> = listOf(),
    val userSearchText: String = "",
    val transliteratedText: String = "",
    val splitWords: List<String> = listOf(),
)