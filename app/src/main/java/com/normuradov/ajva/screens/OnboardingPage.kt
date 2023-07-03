package com.normuradov.ajva.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.normuradov.ajva.R
import com.normuradov.ajva.data.Page

@Composable
fun OnboardingPage(page: Page) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
    Image(
        painter = painterResource(page.image),
        contentDescription = null,
        modifier = Modifier.size(200.dp)
    )
    Spacer(modifier = Modifier.height(20.dp))

    Text(
        text = page.title,
        fontSize = 28.sp, fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = page.description,
        textAlign = TextAlign.Center, fontSize = 14.sp
    )
    Spacer(modifier = Modifier.height(12.dp))

}
}

@Preview
@Composable
fun OnboardingPagePreview() {
    val page = Page(title = "First Page", "Take picture", R.drawable.baseline_photo_camera_100)
    OnboardingPage(page = page)
}