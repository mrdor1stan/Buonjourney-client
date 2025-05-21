package com.mrdor1stan.buonjourney.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrdor1stan.buonjourney.BuonjourneyApplication
import com.mrdor1stan.buonjourney.data.DatabaseRepository
import com.mrdor1stan.buonjourney.data.db.TripWithEventsDetailsDto
import com.mrdor1stan.buonjourney.ui.entities.EventState
import com.mrdor1stan.buonjourney.ui.entities.TicketState
import com.mrdor1stan.buonjourney.ui.entities.TripState
import com.mrdor1stan.buonjourney.ui.entities.map
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeScreenUiState(
    val trips: List<TripState>?,
    val tickets: List<TicketState>?,
    val events: List<EventState>?,
    val selectedTripIndex: Int?
)

class HomeScreenViewModel(
    private val databaseRepository: DatabaseRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            HomeScreenUiState(
                trips = null, tickets = null, events = null, selectedTripIndex = null
            ),
        )
    val uiState = _uiState.asStateFlow()
    private lateinit var trips: List<TripWithEventsDetailsDto>
    private var tripIndex = 0

    fun loadTrip(index: Int) {
        tripIndex = index.coerceIn(trips.indices)
        val events = trips[tripIndex].events.map { it.map() }
        val tickets = trips[tripIndex].events.flatMap { it.tickets }.map { it.map() }
        _uiState.value = uiState.value.copy(events = events, tickets = tickets, selectedTripIndex = tripIndex)
    }

    init {
        viewModelScope.launch {
            databaseRepository.getTripsWithEventsDetails().collect {
                trips = it
                _uiState.value = HomeScreenUiState(
                    trips = it.map { trip -> trip.trip.map() }, tickets = null, events = null, selectedTripIndex = null
                )
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = (this[APPLICATION_KEY] as BuonjourneyApplication)
                    val databaseRepository: DatabaseRepository =
                        application.appContainer.databaseRepository
                    HomeScreenViewModel(
                        databaseRepository = databaseRepository
                    )
                }
            }
    }
}
