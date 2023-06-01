package com.normuradov.ajva

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.michaeltroger.latintocyrillic.Alphabet
import com.michaeltroger.latintocyrillic.LatinCyrillicFactory
import com.normuradov.ajva.screens.SearchScreen
import com.normuradov.ajva.screens.WordDetailScreen
import com.normuradov.ajva.ui.theme.SearchScreenUiState
import com.normuradov.ajva.ui.theme.SearchViewMode
import com.normuradov.ajva.ui.theme.SearchViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val TAG = "WORD_SEARCH_ACTIVITY"

class WordSearchActivity : ComponentActivity() {
    private val viewModel: SearchViewModel by viewModels {
        SearchViewModel.factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var text = intent.getStringExtra("RECOGNIZED_TEXT")!!
            Log.d(TAG, "RECOGNIZED_TEXT $text")
            viewModel.process(text)

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                DictionaryApp(
                    viewModel = viewModel
                )
            }

        }
    }
}


@Composable
fun DictionaryApp(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
) {
    val uiState = viewModel.uiState.collectAsState()
    if (uiState.value.mode == SearchViewMode.Search) {
        SearchScreen(userInput = uiState.value.userSearchText, viewModel = viewModel)
    } else if (uiState.value.mode == SearchViewMode.Detail) {
        WordDetailScreen(word = uiState.value.selectedWord!!, viewModel = viewModel)
    }
}

