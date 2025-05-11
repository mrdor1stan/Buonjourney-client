package com.mrdor1stan.buonjourney.ui

import android.content.Intent
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {
    //TODO: use this when passing pictures for tickets
    fun updateSentText(intent: Intent) {
        intent?.getStringExtra(Intent.EXTRA_TEXT).takeIf {
            intent?.action == Intent.ACTION_SEND && intent.type == "text/plain"
        }
    }
}