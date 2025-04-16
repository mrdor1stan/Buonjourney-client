package com.mrdor1stan.buonjourney

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.Modifier
import com.mrdor1stan.buonjourney.ui.screens.BuonjourneyScreen
import com.mrdor1stan.buonjourney.ui.theme.BuonjourneyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BuonjourneyTheme {
                BuonjourneyScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .imePadding()
                )
            }

            if (intent?.action == Intent.ACTION_SEND && intent.type == "text/plain") {
                handleSendText(intent)
            }
        }
    }

    private fun handleSendText(intent: Intent) {
        intent.toString()
        val tille = intent.getStringExtra(Intent.EXTRA_TITLE)
        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (!sharedText.isNullOrEmpty()) {
            // Do something with the shared link
            Log.d("SharedLink", "Received: ${intent.data}")
            Log.d("SharedLink", "Received: ${intent.categories}")
            Log.d("SharedLink", "Received: ${intent.clipData}")
            Log.d("SharedLink", "Received: ${intent.dataString}")
            Log.d("SharedLink", "Received: ${intent.extras}")
            Log.d("SharedLink", "Received: $sharedText")
        }
    }
}