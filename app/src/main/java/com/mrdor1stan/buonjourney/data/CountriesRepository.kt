package com.mrdor1stan.buonjourney.data

class CountriesRepository(private val service: CountriesApiService) {
    suspend fun getCountries() = service.getCountries().data
}