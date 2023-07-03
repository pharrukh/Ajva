package com.normuradov.ajva.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.normuradov.ajva.R
import com.normuradov.ajva.data.Page
import com.normuradov.ajva.ui.theme.AjvaTheme

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalAnimationApi
@Composable
fun OnboardingUI(
    onGettingStartedClick: () -> Unit,
    onSkipClicked: () -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = 0)
    val pageCount = onboardPages.size

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Skip", modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onSkipClicked() }
        )

        HorizontalPager(
            pageCount = pageCount,
            state = pagerState,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)

        ) { page ->
            OnboardingPage(page = onboardPages[page])
        }

        Row(
            Modifier
                .height(50.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(20.dp)

                )
            }
        }

        AnimatedVisibility(visible = pagerState.currentPage == pageCount - 1) {
            OutlinedButton(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp), onClick = onGettingStartedClick,
                colors = ButtonDefaults.outlinedButtonColors(
                )
            ) {
                Text(text = "Get Started")
            }
        }

    }
}

val onboardPages = listOf(
    Page(
        "Greetings from Adja's creator!",
        "Thanks for downloading the app!\n Let me show you how it works.",
        R.drawable.onboarding_logo_play_store
    ),
    Page(
        "1. Take a picture of text",
        "Point your phone to some text and click the button to take a picture. The app will recognize the text and translate it for you.",
        R.drawable.onboarding_take_picture
    ),
    Page(
        "2. Recognition",
        "The app will automatically recognize the text and show the words it managed to see in the picture.",
        R.drawable.onboarding_recognition
    ),
    Page(
        "3. Translation / Interpretation",
        "Click on any word that the app managed to recognize and you will see the translation of that word in Uzbek.",
        R.drawable.onboarding_translation_interpretation
    ),
    Page(
        "4. Dictionary",
        "You can also search for any word in the dictionary by typing a word.",
        R.drawable.onboarding_dictionary
    )
)


// add preview for the onboarding screen
@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun OnboardingPreview() {
    AjvaTheme {
        OnboardingUI(onGettingStartedClick = {}, onSkipClicked = {})
    }
}