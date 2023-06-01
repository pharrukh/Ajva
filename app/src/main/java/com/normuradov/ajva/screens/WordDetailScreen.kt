package com.normuradov.ajva.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.normuradov.ajva.R
import com.normuradov.ajva.data.Word
import com.normuradov.ajva.data.examples.WORDS
import com.normuradov.ajva.ui.theme.SearchViewModel
import kotlinx.coroutines.launch

@Composable
fun WordDetailScreen(modifier: Modifier = Modifier, word: Word, viewModel: SearchViewModel) {
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Column {

            Row {
                IconButton(onClick = {
                    coroutineScope.launch {
                        viewModel.navigateToSearchScreen()
                    }
                }) {
                    Icon(
                        painterResource(id = R.drawable.baseline_arrow_back_24),
                        contentDescription = "back",
                        modifier = modifier.size(20.dp)
                    )
                }
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = word.word ?: "",
                    style = MaterialTheme.typography.displayLarge,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                modifier = modifier.padding(8.dp),
                text = word.meaning ?: "",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
