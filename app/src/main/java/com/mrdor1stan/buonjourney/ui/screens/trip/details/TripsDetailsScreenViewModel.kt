package com.mrdor1stan.buonjourney.ui.screens.trip.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrdor1stan.buonjourney.BuonjourneyApplication
import com.mrdor1stan.buonjourney.data.DatabaseRepository
import com.mrdor1stan.buonjourney.ui.entities.TripState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TripsDetailsScreenUiState(
    val trip: TripState?
)

class TripsDetailsScreenViewModel(
    private val databaseRepository: DatabaseRepository,
    private val tripId: Long
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        TripsDetailsScreenUiState(trip = null)
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            databaseRepository.getTrip(tripId).collect { trip ->
                _uiState.value = uiState.value.copy(trip = trip.let {
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
        fun Factory(tripId: Long) : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BuonjourneyApplication)
                val databaseRepository: DatabaseRepository =
                    application.appContainer.databaseRepository
                TripsDetailsScreenViewModel(
                    databaseRepository = databaseRepository,
                    tripId = tripId
                )
            }
        }
    }

}