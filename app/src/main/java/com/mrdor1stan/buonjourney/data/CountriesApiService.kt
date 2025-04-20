package com.mrdor1stan.buonjourney.data

import retrofit2.http.GET


interface CountriesApiService {
    @GET("flag/unicode")
    suspend fun getCountries(): CountriesResponse
}