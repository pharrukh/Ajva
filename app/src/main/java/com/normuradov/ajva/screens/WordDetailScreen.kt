package com.normuradov.ajva.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.normuradov.ajva.data.Word
import com.normuradov.ajva.data.examples.WORDS

@Composable
fun WordDetailScreen(modifier: Modifier = Modifier, word: Word) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Column {
            Row {
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = word.word,
                    style = MaterialTheme.typography.displayLarge,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                modifier = modifier.padding(8.dp),
                text = word.meaning,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@Preview
@Composable
fun WordDetailScreenPreview() {
    WordDetailScreen(word = WORDS[0])
}

