package com.normuradov.ajva

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.normuradov.ajva.screens.SearchScreen
import com.normuradov.ajva.screens.WordDetailScreen
import com.normuradov.ajva.ui.theme.AjvaTheme
import com.normuradov.ajva.ui.theme.SearchViewMode
import com.normuradov.ajva.ui.theme.SearchViewModel
import kotlinx.coroutines.launch

private const val TAG = "WORD_SEARCH_ACTIVITY"

class WordSearchActivity : ComponentActivity() {
    private val viewModel: SearchViewModel by viewModels {
        SearchViewModel.factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            var text = intent.getStringExtra("RECOGNIZED_TEXT")!!
//            Log.d(TAG, "RECOGNIZED_TEXT $text")
            val text = "а"
            viewModel.process(text)

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                AjvaTheme {
                    DictionaryApp(
                        viewModel = viewModel
                    )
                }
            }

        }
    }
}


@Composable
fun DictionaryApp(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    when (uiState.mode) {
        SearchViewMode.Search ->
            SearchScreen()
        SearchViewMode.Detail -> {
            val coroutineScope = rememberCoroutineScope()
            WordDetailScreen(word = uiState.selectedWord!!, onBackClick = {
                coroutineScope.launch {
                    viewModel.navigateToSearchScreen()
                }
            })
        }
        SearchViewMode.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

