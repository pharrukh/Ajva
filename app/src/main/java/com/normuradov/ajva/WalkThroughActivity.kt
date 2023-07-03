package com.normuradov.ajva

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.normuradov.ajva.screens.Stage
import com.normuradov.ajva.screens.WalkThroughViewModel
import com.normuradov.ajva.ui.theme.AjvaTheme
import com.normuradov.ajva.ui.theme.SearchViewModel

private const val TAG = "WALK_THROUGH_ACTIVITY"

class WalkThroughActivity : ComponentActivity() {
    private val viewModel: WalkThroughViewModel by viewModels {
        WalkThroughViewModel.factory
    }

    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModel.factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var text = intent.getStringExtra("RECOGNIZED_TEXT")!!
        Log.v(TAG, "RECOGNIZED_TEXT $text")

        viewModel.setText(text)

        setContent {
            AjvaTheme {
                val uiState by viewModel.uiState.collectAsState()

                if (uiState.recognizedText.isEmpty()) {
                    WalkThroughPage(
                        title = "Nothing Recognized",
                        description = "Please go back to the camera and try again.",
                        onPreviousClick = {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        },
                        shouldHideNext = true
                    )
                } else {

                    when (uiState.stage) {
                        Stage.ShowingLatinOrCyrillic -> WalkThroughPage(
                            title = "Latin or Cyrillic",
                            description = "I currently recognize only Latin alphabet. I will now transliterate it to Cyrillic.",
                            onNextClick = {
                                viewModel.moveToRecognizedText()
                            }, shouldHidePrevious = true
                        )

                        Stage.ShowingRecognizedText -> {

                            WalkThroughPage(
                                title = "Recognized Text",
                                description = uiState.recognizedText,
                                onPreviousClick = {
                                    viewModel.moveToLatinOrCyrillic()
                                },
                                onNextClick = {
                                    viewModel.moveToParsing()
                                })

                        }


                        Stage.ShowingParsedWords -> WalkThroughPage(
                            title = "Parsed Words (${uiState.parsedWords.size})",
                            description = uiState.parsedWords.joinToString(separator = " "),
                            onPreviousClick = {
                                viewModel.moveToRecognizedText()
                            },
                            onNextClick = {
                                viewModel.moveToFoundWords()
                            })

                        Stage.SearchingForWords -> WalkThroughPage(
                            title = "Searching for words (${uiState.foundWords.size})",
                            description =
                            "I am now searching for words in the dictionary. This may take a while. Already found ${uiState.foundWords.size} words.",
                            showLoader = true,
                            shouldHidePrevious = true,
                            shouldHideNext = true
                        )

                        Stage.ShowingFoundWords -> WalkThroughPage(
                            title = "Found words (${uiState.foundWords.size})",
                            description = uiState.foundWords.map { it.word?.lowercase() }
                                .joinToString(separator = ", "),
                            onPreviousClick = {
                                viewModel.moveToParsing()
                            },
                            onNextClick = {
                                val application = this.application as DictionaryApplication
                                application.sharedFoundWords = uiState.foundWords
                                val intent = Intent(this, WordSearchActivity::class.java)
                                startActivity(intent)
                            }
                        )
                    }
                }

            }
        }
    }


    @Composable
    fun WalkThroughPage(
        title: String,
        description: String,
        onPreviousClick: () -> Unit = {}, onNextClick: () -> Unit = {},
        shouldHidePrevious: Boolean = false,
        shouldHideNext: Boolean = false,
        showLoader: Boolean = false,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
            {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.displayLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.8f)
                        .verticalScroll(state = rememberScrollState())
                ) {
                    Text(
                        text = description,
                        textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                if (showLoader) {
                    LoadingView()
                }

                Row(
                    modifier = Modifier.align(Alignment.End)
                ) {
                    if (!shouldHidePrevious) {
                        Button(onClick = onPreviousClick) {
                            Text(text = "Previous")
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    if (!shouldHideNext) {
                        Button(onClick = onNextClick) {
                            Text(text = "Next")
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun LoadingView() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

    @Preview
    @Composable
    fun WalkThroughPagePreview() {
        AjvaTheme {
            WalkThroughPage(
                title = "Latin or Cyrillic",
                description = "I currently recognize only Latin alphabet. I will now transliterate it to Cyrillic.\nI currently recognize only Latin alphabet. I will now transliterate it to Cyrillic.\nI currently recognize only Latin alphabet. I will now transliterate it to Cyrillic.\nI currently recognize only Latin alphabet. I will now transliterate it to Cyrillic.\nI currently recognize only Latin alphabet. I will now transliterate it to Cyrillic.\nI currently recognize only Latin alphabet. I will now transliterate it to Cyrillic.\nI currently recognize only Latin alphabet. I will now transliterate it to Cyrillic.\nI currently recognize only Latin alphabet. I will now transliterate it to Cyrillic.\nI currently recognize only Latin alphabet. I will now transliterate it to Cyrillic.\nI currently recognize only Latin alphabet. I will now transliterate it to Cyrillic.I will now transliterate it to Cyrillic.\n" +
                        "I currently recognize only Latin alphabet. I will now transliterate it to Cyrillic.\n" +
                        "I currently recognize only Latin alphabet. I will now transliterate it to Cyrillic.\n" +
                        "I currently recognize only Latin alphabet. I will now transliterate it to Cyrillic.\n" +
                        "I currently recognize only Latin alphabet. I will now transliterate it to Cyrillic.\n" +
                        "I currently recognize only Latin alphabet. I will now transliterate it to Cyrillic.\n" +
                        "I currently recognize only Latin alphabet. I will now transliterate it to Cyrillic.\n" +
                        "I currently recognize only Latin alphabet. I will now transliterate it to Cyrillic.",
                onNextClick = {}
            )
        }
    }
}

