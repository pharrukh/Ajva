package com.normuradov.ajva.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.normuradov.ajva.data.Word
import com.normuradov.ajva.ui.theme.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    userInput: String = "",
    words: List<Word> = listOf(),
    viewModel: SearchViewModel,
) {
    Log.v("SEARCH SCREEN", "Passed words count: ${words.size}")
    val state = viewModel.uiState.collectAsState()
    Log.v("SEARCH SCREEN", "Recomposition: ${state.value.foundWords.size} words")
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            modifier = modifier
                .padding(4.dp)
                .width(350.dp),
            value = userInput,
            onValueChange = {
                viewModel.updateText(it)
                viewModel.searchForWordsByUserInput()
                Log.v("DEBUG", viewModel.uiState.value.userSearchText)
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

        LazyColumn {
            items(
                items = state.value.foundWords,
                key = { it.word!! }) {
                WordCard(word = it, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun WordCard(modifier: Modifier = Modifier, word: Word,viewModel: SearchViewModel) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(30.dp).clickable(onClick = {viewModel.navigateToWord(word)}) ,
    ) {
        Row(modifier = modifier.fillMaxSize()) {
            Text(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 4.dp),
                text = word.word!!,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,

                )
        }
    }

}


//@Preview
//@Composable
//fun SearchScreenPreview() {
//    SearchScreen()
//}
//
