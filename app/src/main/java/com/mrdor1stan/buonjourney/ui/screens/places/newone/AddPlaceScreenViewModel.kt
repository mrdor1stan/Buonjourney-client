package com.mrdor1stan.buonjourney.ui.screens.places.newone

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrdor1stan.buonjourney.BuonjourneyApplication
import com.mrdor1stan.buonjourney.data.CountriesRepository
import com.mrdor1stan.buonjourney.data.CountryResponse
import com.mrdor1stan.buonjourney.data.DatabaseRepository
import com.mrdor1stan.buonjourney.data.db.CityDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "AddCityScreenViewModel"

data class CountryState(val name: String, val flag: String?)

fun CountryResponse.map() = CountryState(name, flag)

data class AddCityScreenUiState(
    val selectedPlaceType: PlaceType,
    val title: String,
    val isAddButtonEnabled: Boolean,
    val selectedCountry: String,
    val countriesList: List<CountryState>
)

class AddCityScreenViewModel(
    private val databaseRepository: DatabaseRepository,
    private val countriesRepository: CountriesRepository,
    private val cityId: String?,
    placeLink: String?,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            AddCityScreenUiState(
                selectedPlaceType = PlaceType.City,
                title = "",
                isAddButtonEnabled = false,
                selectedCountry = "",
                countriesList = listOf()
            ),
        )

    val uiState = _uiState.asStateFlow()

    init {

        Log.d(TAG, "cityId: $cityId")
        viewModelScope.launch {
            if (cityId != null) {
                databaseRepository.getCity(cityId).collect {
                    _uiState.value =
                        uiState.value.copy(title = it.name, selectedCountry = it.country)
                    Log.d(TAG, uiState.value.toString())
                }
            }
        }

        viewModelScope.launch {
            try {
                val countries = countriesRepository.getCountries().sortedBy { it.name }
                Log.d(TAG, countries.joinToString { it.toString() })
                _uiState.value = uiState.value.copy(countriesList = countries.map { it.map() })
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
    }

    fun updateTitle(value: String) {
        _uiState.value = uiState.value.copy(title = value)
        validateAddButton()
    }

    fun updateCountry(value: String) {
        _uiState.value = uiState.value.copy(selectedCountry = value)
        validateAddButton()
    }

    fun updatePlaceType(type: PlaceType) {
        _uiState.value = uiState.value.copy(selectedPlaceType = type)
        validateAddButton()
    }

    private fun validateAddButton() {
        _uiState.value =
            uiState.value.copy(
                isAddButtonEnabled = uiState.value.title.isNotEmpty() && uiState.value.selectedCountry.isNotEmpty(),
            )
    }

    suspend fun addCity() {
        when (cityId) {
            null -> databaseRepository.addCity(
                CityDto(
                    name = uiState.value.title,
                    country = uiState.value.selectedCountry
                )
            )

            else -> databaseRepository.updateCity(
                CityDto(
                    name = uiState.value.title,
                    country = uiState.value.selectedCountry,
                    id = cityId
                )
            )
        }

    }

    companion object {
        fun Factory(cityId: String?, placeLink: String?): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = (this[APPLICATION_KEY] as BuonjourneyApplication)
                    val databaseRepository: DatabaseRepository =
                        application.appContainer.databaseRepository
                    val countriesRepository: CountriesRepository =
                        application.appContainer.countriesRepository
                    AddCityScreenViewModel(
                        databaseRepository = databaseRepository,
                        countriesRepository = countriesRepository,
                        cityId = cityId,
                        placeLink = placeLink
                    )
                }
            }
    }
}
