package com.mrdor1stan.buonjourney.ui

import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.mrdor1stan.buonjourney.data.db.DbTypeConverters
import com.mrdor1stan.buonjourney.data.db.EventDto

private const val TAG = "MainActivityViewModel"

class MainActivityViewModel : ViewModel() {

    //TODO: use this when passing pictures for tickets
    fun updateSentText(intent: Intent) {
        intent?.getStringExtra(Intent.EXTRA_TEXT).takeIf {
            intent?.action == Intent.ACTION_SEND && intent.type == "text/plain"
        }
    }
}