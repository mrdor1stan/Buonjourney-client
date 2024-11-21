package com.mrdor1stan.buonjourney

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
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
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}