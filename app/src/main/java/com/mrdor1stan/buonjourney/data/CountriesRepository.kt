package com.mrdor1stan.buonjourney.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

private const val TAG = "CountriesRepository"

class CountriesRepository(private val service: CountriesApiService, private val context: Context) {
    suspend fun getCountries(): List<CountryResponse> {
        try {
            return service.getCountries().data
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }

        try {
            return parseLocalCountriesJson().data
        } catch (e: IOException) {
            Log.e(TAG, e.toString())
        }

        return listOf()
    }

    private fun parseLocalCountriesJson(): CountriesResponse {
        val jsonString =
            context
                .assets
                .open("countries.json")
                .bufferedReader()
                .use { it.readText() }
        val listCountryType = object : TypeToken<CountriesResponse>() {}.type
        return Gson().fromJson(jsonString, listCountryType)
    }
}