package com.mrdor1stan.buonjourney.data

import android.content.Context
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.mrdor1stan.buonjourney.BuildConfig
import com.mrdor1stan.buonjourney.ui.entities.CityState

interface PlaceLinkRepository {
    fun getInfo(link: String): CityState
}

class GoogleMapsPlaceLinkRepository(context: Context): PlaceLinkRepository {

    private var placesClient: PlacesClient

    init {
        Places.initialize(context, BuildConfig.MAPS_API_KEY)
        placesClient = Places.createClient(context)
    }

    override fun getInfo(link: String): CityState {
        TODO("Not yet implemented")
    }

}