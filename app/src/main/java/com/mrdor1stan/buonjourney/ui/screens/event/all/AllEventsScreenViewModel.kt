package com.mrdor1stan.buonjourney.ui.screens.event.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrdor1stan.buonjourney.BuonjourneyApplication
import com.mrdor1stan.buonjourney.data.DatabaseRepository
import com.mrdor1stan.buonjourney.ui.entities.EventState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AllEventsScreenUiState(
    val results: List<EventState>,
)

class AllEventsScreenViewModel(
    private val tripId: Long?,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            AllEventsScreenUiState(results = listOf()),
        )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            (
                tripId?.let {
                    databaseRepository.getEventsByTrip(tripId)
                } ?: databaseRepository.getEvents()
            ).collect { events ->
                _uiState.value =
                    uiState.value.copy(
                        results =
                            events.map {
                                EventState(
                                    title = it.title,
                                    dateTime = it.dateTime,
                                    description = it.description,
                                    address = it.address,
                                )
                            },
                    )
            }
        }
    }

    suspend fun deleteEvent(eventId: Int) {
       // databaseRepository.deleteEvent(eventId)
    }

    companion object {
        fun Factory(tripId: Long?): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = (this[APPLICATION_KEY] as BuonjourneyApplication)
                    val databaseRepository: DatabaseRepository =
                        application.appContainer.databaseRepository
                    AllEventsScreenViewModel(
                        tripId = tripId,
                        databaseRepository = databaseRepository,
                    )
                }
            }
    }
}
