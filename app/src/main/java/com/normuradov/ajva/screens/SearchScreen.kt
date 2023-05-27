package com.normuradov.ajva.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.normuradov.ajva.data.Word
import com.normuradov.ajva.data.examples.WORDS

@Composable
fun SearchScreen(modifier: Modifier = Modifier, words: List<Word> = listOf()) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(value = "", onValueChange = {})
        Spacer(modifier = modifier.padding(8.dp))
        LazyColumn {
            items(
                items = words,
                key = { it.id }) {
                WordCard(word = it)
            }
        }
    }
}

@Composable
fun WordCard(modifier: Modifier = Modifier, word: Word) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(30.dp),
    ) {
        Row(modifier = modifier.fillMaxSize()) {
            Text(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 4.dp),
                text = word.word,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,

                )
        }
    }

}


@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(words = WORDS)
}

