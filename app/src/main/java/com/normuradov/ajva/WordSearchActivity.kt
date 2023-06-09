package com.normuradov.ajva

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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

        val application = this.application as DictionaryApplication
        viewModel.setWords(application.sharedFoundWords)

        setContent {
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
            ) {

                AjvaTheme {
                    DictionaryApp(
                        viewModel = viewModel,
                        onCameraFABClick = {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
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
    onCameraFABClick: () -> Unit = {},
) {

    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCameraFABClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_photo_camera_100),
                    contentDescription = "Camera"
                )
            }
        }
    ) { contentPadding ->
        AnimatedVisibility(
            visible = uiState.mode == SearchViewMode.Search,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            if (uiState.mode == SearchViewMode.Search) {


                SearchScreen(modifier = Modifier.padding(contentPadding))
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

                WordDetailScreen(
                    modifier = Modifier.padding(contentPadding),
                    word = selectedWord,
                    onBackClick = {
                        coroutineScope.launch {
                            viewModel.navigateToSearchScreen()
                        }
                    })
            }
        }
    }
}

@Preview
@Composable
fun DictionaryAppPreview() {
    AjvaTheme {
        DictionaryApp()
    }
}

