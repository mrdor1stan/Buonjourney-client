package com.mrdor1stan.buonjourney.ui.screens.places.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrdor1stan.buonjourney.BuonjourneyApplication
import com.mrdor1stan.buonjourney.data.DatabaseRepository
import com.mrdor1stan.buonjourney.ui.entities.CityState
import com.mrdor1stan.buonjourney.ui.entities.map
import com.mrdor1stan.buonjourney.ui.screens.trip.all.AllTripsScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AllCitiesScreenUiState(
    val results: List<CityState>
)

class AllCitiesScreenViewModel(
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AllCitiesScreenUiState(results = listOf())
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            databaseRepository.getCities().collect { cities ->
                _uiState.value = uiState.value.copy(results = cities.map {
                    it.map()
                })
            }
        }
    }

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BuonjourneyApplication)
                val databaseRepository: DatabaseRepository =
                    application.appContainer.databaseRepository
                AllCitiesScreenViewModel(
                    databaseRepository = databaseRepository
                )
            }
        }
    }

}