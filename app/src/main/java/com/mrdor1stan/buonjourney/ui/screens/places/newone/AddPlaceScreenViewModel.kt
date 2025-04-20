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
    val title: String,
    val isAddButtonEnabled: Boolean,
    val selectedCountry: String,
    val countriesList: List<CountryState>
)

class AddCityScreenViewModel(
    private val databaseRepository: DatabaseRepository,
    private val countriesRepository: CountriesRepository,
    cityId: String?,
    placeLink: String?,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            AddCityScreenUiState(
                title = "",
                isAddButtonEnabled = false,
                selectedCountry = "",
                countriesList = listOf()
            ),
        )

    init {
        cityId?.let {
            viewModelScope.launch {
                databaseRepository.getCity(it).collect {
                    _uiState.value =
                        uiState.value.copy(title = it.name, selectedCountry = it.country)
                    validateAddButton()
                }
            }
        }

        viewModelScope.launch {
            try {
                val countries = countriesRepository.getCountries()
                _uiState.value = uiState.value.copy(countriesList = countries.map { it.map() })
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
    }

    val uiState = _uiState.asStateFlow()

    fun updateTitle(value: String) {
        _uiState.value = uiState.value.copy(title = value)
        validateAddButton()
    }

    fun updateCountry(value: String) {
        _uiState.value = uiState.value.copy(selectedCountry = value)
        validateAddButton()
    }

    private fun validateAddButton() {
        _uiState.value =
            uiState.value.copy(
                isAddButtonEnabled = uiState.value.title.isNotEmpty() && uiState.value.selectedCountry.isNotEmpty(),
            )
    }

    suspend fun addCity() {
        databaseRepository.addCity(
            CityDto(
                name = uiState.value.title,
                country = uiState.value.selectedCountry
            ),
        )
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
