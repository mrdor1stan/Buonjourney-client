package com.mrdor1stan.buonjourney.data

import com.google.gson.annotations.SerializedName

data class CountriesResponse(
    val data: List<CountryResponse>
)

data class CountryResponse(
    val name: String,
    @SerializedName("unicodeFlag") val flag: String?
)

