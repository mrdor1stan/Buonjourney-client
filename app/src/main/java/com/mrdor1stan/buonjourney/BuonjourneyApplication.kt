package com.mrdor1stan.buonjourney

import android.app.Application
import com.mrdor1stan.buonjourney.data.AppContainer

class BuonjourneyApplication: Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(context = this)
    }

}