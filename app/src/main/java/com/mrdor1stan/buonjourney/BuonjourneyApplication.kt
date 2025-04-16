package com.mrdor1stan.buonjourney

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.mrdor1stan.buonjourney.data.AppContainer

class BuonjourneyApplication : Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY)
        appContainer = AppContainer(context = this)
    }

}