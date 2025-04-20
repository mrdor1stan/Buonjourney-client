package com.mrdor1stan.buonjourney.data

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.mrdor1stan.buonjourney.data.db.BuonjourneyDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val USER_PREFERENCES_NAME = "user_preferences_name"
const val COUNTRIES_BASE_URL = "https://countriesnow.space/api/v0.1/countries/"

class AppContainer(
    context: Context,
) {
    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME,
    )

    private val countriesRetrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(COUNTRIES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val countriesRepository: CountriesRepository by lazy {
        CountriesRepository(countriesRetrofit.create(CountriesApiService::class.java))
    }

    val databaseRepository: DatabaseRepository by lazy {
        DatabaseRepository(BuonjourneyDatabase.getDatabase(context))
    }
    val userRepository: UserRepository by lazy {
        UserRepository(context.dataStore)
    }
    val mapsPlaceLinkRepository: PlaceLinkRepository by lazy {
        GoogleMapsPlaceLinkRepository(context.applicationContext)
    }
}
