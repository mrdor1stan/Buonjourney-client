package com.mrdor1stan.buonjourney.ui.screens.trip.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrdor1stan.buonjourney.BuonjourneyApplication
import com.mrdor1stan.buonjourney.data.DatabaseRepository
import com.mrdor1stan.buonjourney.data.db.EventDto
import com.mrdor1stan.buonjourney.ui.entities.TripState
import com.mrdor1stan.buonjourney.ui.entities.map
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "TripsDetailsScreenViewModel"

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
                _uiState.value = uiState.value.copy(
                    trip = trip?.map()
                )
            }
        }
    }

    suspend fun deleteTrip() {
        databaseRepository.deleteTrip(tripId)
    }

    companion object {
        fun Factory(tripId: Long): ViewModelProvider.Factory = viewModelFactory {
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