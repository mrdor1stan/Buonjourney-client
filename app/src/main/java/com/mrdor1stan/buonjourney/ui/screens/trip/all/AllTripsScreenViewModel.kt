package com.mrdor1stan.buonjourney.ui.screens.trip.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrdor1stan.buonjourney.BuonjourneyApplication
import com.mrdor1stan.buonjourney.data.DatabaseRepository
import com.mrdor1stan.buonjourney.data.UserRepository
import com.mrdor1stan.buonjourney.data.db.TripsDetailsDto
import com.mrdor1stan.buonjourney.ui.entities.TripDetails
import com.mrdor1stan.buonjourney.ui.entities.TripState
import com.mrdor1stan.buonjourney.ui.screens.login.LoginScreenUiState
import com.mrdor1stan.buonjourney.ui.screens.login.LoginScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AllTripsScreenUiState(
    val results: List<TripState>
)

class AllTripsScreenViewModel(
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AllTripsScreenUiState(results = listOf())
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            databaseRepository.getTrips().collect { trips ->
                _uiState.value = uiState.value.copy(results = trips.map {
                    TripState(
                        startDate = it.trip.startDate,
                        endDate = it.trip.endDate,
                        tickets = it.tickets,
                        title = it.trip.title,
                        destination = it.place.name,
                        packingLists = it.packingLists,
                        status = it.trip.status,
                        events = it.events
                    )
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
                AllTripsScreenViewModel(
                    databaseRepository = databaseRepository
                )
            }
        }
    }

}