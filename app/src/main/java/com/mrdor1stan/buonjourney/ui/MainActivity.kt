package com.mrdor1stan.buonjourney.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier
import com.mrdor1stan.buonjourney.ui.screens.BuonjourneyScreen
import com.mrdor1stan.buonjourney.ui.theme.BuonjourneyTheme

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.updateSentText(intent)
        enableEdgeToEdge()
        setContent {
            BuonjourneyTheme {
                BuonjourneyScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .imePadding(),
                    sharedText = viewModel.sharedText
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        viewModel.updateSentText(intent)
    }


}