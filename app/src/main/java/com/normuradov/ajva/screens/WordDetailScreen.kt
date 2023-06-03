package com.normuradov.ajva.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.normuradov.ajva.R
import com.normuradov.ajva.data.Word
import com.normuradov.ajva.data.examples.WORDS
import com.normuradov.ajva.ui.theme.AjvaTheme

@Composable
fun WordDetailScreen(
    modifier: Modifier = Modifier,
    word: Word,
    onBackClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Column {
            Row {
                IconButton(
                    onClick = onBackClick,
                    modifier = modifier
                        .padding(8.dp)
                        .align(alignment = androidx.compose.ui.Alignment.CenterVertically)
                ) {
                    Icon(
                        painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = "back",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = modifier.size(40.dp),
                    )
                }
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = word.word ?: "",
                    style = MaterialTheme.typography.displayLarge,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    modifier = modifier.padding(16.dp, 8.dp, 16.dp, 0.dp),
                    text = word.meaning ?: "",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun WordDetailScreenPreview() {
    AjvaTheme {
        WordDetailScreen(
            word = WORDS[0],
            onBackClick = {}
        )
    }
}