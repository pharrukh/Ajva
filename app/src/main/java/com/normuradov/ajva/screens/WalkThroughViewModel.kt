package com.normuradov.ajva.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.normuradov.ajva.DictionaryApplication
import com.normuradov.ajva.data.Word
import com.normuradov.ajva.data.WordRepository
import com.normuradov.ajva.utils.transliterate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val TAG = "WALK_THROUGH_VIEW_MODEL"

class WalkThroughViewModel(
    private val wordRepository: WordRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(WalkThroughUiState())
    val uiState: StateFlow<WalkThroughUiState> = _uiState

    fun setText(recognizedText: String) {
        val transliteratedText = transliterate(recognizedText.lowercase())
        _uiState.update { _uiState.value.copy(recognizedText = transliteratedText) }
    }

    fun moveToLatinOrCyrillic() {
        _uiState.update { _uiState.value.copy(stage = Stage.ShowingLatinOrCyrillic) }
    }

    fun moveToRecognizedText() {
        _uiState.update { _uiState.value.copy(stage = Stage.ShowingRecognizedText) }
    }

    fun moveToFoundWords() {
        if(!_uiState.value.foundWords.isEmpty()) {
            _uiState.update { _uiState.value.copy(stage = Stage.ShowingFoundWords) }
            return
        }

        _uiState.update { _uiState.value.copy(stage = Stage.SearchingForWords) }

        viewModelScope.launch {
            val result = mutableSetOf<Word>()
            for (recognizedWord in _uiState.value.parsedWords) {
                Log.v(TAG, "recognizedWord: $recognizedWord")
                val possibleMatches = wordRepository.search(recognizedWord)
                if (possibleMatches.isEmpty()) continue
                val word = possibleMatches[0]
                Log.v(TAG, "word: ${word.meaning}")
                val words = wordRepository.fullTextSearch(word.word!!)
                Log.v(TAG, "full text search: ${words.joinToString { it.word!! }}")
                if (words.isNotEmpty())
                    result.add(words[0])
                _uiState.update { _uiState.value.copy(foundWords = result.toList()) }
            }

            _uiState.update { _uiState.value.copy(stage = Stage.ShowingFoundWords) }
        }
    }

    fun moveToParsing() {
        val recognizedText = _uiState.value.recognizedText
        val parsedWords = recognizedText.split("\\s+".toRegex())
            .map { word -> word.replace("[^а-яА-Я]".toRegex(), "") }
            .filter { word -> word.isNotEmpty() }.filter { it.length > 2 }.sorted()

        _uiState.update {
            _uiState.value.copy(
                stage = Stage.ShowingParsedWords,
                parsedWords = parsedWords
            )
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as DictionaryApplication)
                WalkThroughViewModel(wordRepository = application.container.wordRepository)
            }
        }
    }
}

enum class Stage {
    ShowingLatinOrCyrillic,
    ShowingRecognizedText,
    ShowingParsedWords,
    SearchingForWords,
    ShowingFoundWords
}

data class WalkThroughUiState(
    val latinOrCiyrillic: String = "latin",
    val stage: Stage = Stage.ShowingLatinOrCyrillic,
    val recognizedText: String = "",
    val parsedWords: List<String> = emptyList(),
    val transliteratedWords: List<String> = emptyList(),
    val foundWords: List<Word> = emptyList(),
    val isLoading: Boolean = false,
)