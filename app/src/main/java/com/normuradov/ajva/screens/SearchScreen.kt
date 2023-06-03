package com.normuradov.ajva.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.normuradov.ajva.data.Word
import com.normuradov.ajva.ui.theme.AjvaTheme
import com.normuradov.ajva.ui.theme.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    words: List<Word> = listOf(),
    viewModel: SearchViewModel = viewModel(),
) {

    Log.d("SEARCH SCREEN", "Passed words count: ${words.size}")
    val state by viewModel.uiState.collectAsState()
    Log.d("SEARCH SCREEN", "Recomposition: ${state.foundWords.size} words")

    // Remember the TextField value
    var textFieldValue by rememberSaveable { mutableStateOf(state.userSearchText) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = modifier
                .padding(4.dp)
                .width(350.dp),
            value = textFieldValue,
            onValueChange = {
                textFieldValue = it
                viewModel.updateText(it)
                Log.d("DEBUG", it)
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            shape = MaterialTheme.shapes.large,
            singleLine = true,
        )

        Spacer(modifier = modifier.padding(8.dp))

        AnimatedVisibility(
            visible = state.isLoading,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        }

        LazyColumn {
            items(
                items = state.foundWords,
                key = { it.id }) {
                WordCard(word = it, onClick = { viewModel.navigateToWord(it) })
            }
        }
    }
}

@Composable
fun WordCard(
    modifier: Modifier = Modifier,
    word: Word,
    onClick: (word: Word) -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(onClick = { onClick(word) })
            .padding(4.dp),
    ) {
        Row(
            modifier = modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = modifier
                    .fillMaxSize()
                    .padding(bottom = 2.dp, start = 8.dp),
                text = word.word!!,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Preview
@Composable
fun PreviewSearchScreen() {
    AjvaTheme {
        SearchScreen()
    }
}

