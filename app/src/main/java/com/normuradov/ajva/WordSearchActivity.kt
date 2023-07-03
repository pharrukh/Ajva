package com.normuradov.ajva

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
//            viewModel.toastEvent.observe(this) { toastEvent ->
//                toastEvent?.let { toast ->
//                    Toast.makeText(this.baseContext, toast.message, toast.duration).show()
//                }
//            }

            var text = intent.getStringExtra("RECOGNIZED_TEXT")!!
            Log.v(TAG, "RECOGNIZED_TEXT $text")

            // Display the result in a Toast
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()

            viewModel.showLoader()
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

    AnimatedVisibility(
        visible = uiState.mode == SearchViewMode.Search,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        if (uiState.mode == SearchViewMode.Search) {
            SearchScreen()
        }
    }


    AnimatedVisibility(
        visible = uiState.mode == SearchViewMode.Detail,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        val selectedWord = uiState.selectedWord
        if (uiState.mode == SearchViewMode.Detail && selectedWord != null) {
            val coroutineScope = rememberCoroutineScope()
            WordDetailScreen(word = selectedWord, onBackClick = {
                coroutineScope.launch {
                    viewModel.navigateToSearchScreen()
                }
            })
        }
    }
}

