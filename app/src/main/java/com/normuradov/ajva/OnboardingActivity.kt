package com.normuradov.ajva

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.normuradov.ajva.screens.OnboardingUI
import com.normuradov.ajva.ui.theme.AjvaTheme

class OnboardingActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val context = LocalContext.current
            val onboardingComplete = remember { mutableStateOf(isOnboardingComplete(context)) }

            if (onboardingComplete.value) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                AjvaTheme {
                    OnboardingUI(
                        onGettingStartedClick = {
                            startActivity(Intent(this, MainActivity::class.java))
                            setOnboardingComplete(context)
                            onboardingComplete.value = true
                            finish()
                        },
                        onSkipClicked = {
                            startActivity(Intent(this, MainActivity::class.java))
                            setOnboardingComplete(context)
                            onboardingComplete.value = true
                            finish()
                        }
                    )
                }
            }
        }
    }

    private fun isOnboardingComplete(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences("Onboarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Completed", false)
    }

    private fun setOnboardingComplete(context: Context) {
        val sharedPref = context.getSharedPreferences("Onboarding", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("Completed", true)
            commit()
        }
    }
}

