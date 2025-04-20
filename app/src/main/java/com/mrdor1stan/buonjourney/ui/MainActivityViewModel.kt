package com.mrdor1stan.buonjourney.ui

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
    var sharedText by mutableStateOf<String?>(null)
        private set

    fun updateSentText(intent: Intent) {
        sharedText = intent?.getStringExtra(Intent.EXTRA_TEXT).takeIf {
            intent?.action == Intent.ACTION_SEND && intent.type == "text/plain"
        }
    }
}