package com.mrdor1stan.buonjourney.ui.screens.event.details

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrdor1stan.buonjourney.BuonjourneyApplication
import com.mrdor1stan.buonjourney.data.DatabaseRepository
import com.mrdor1stan.buonjourney.data.db.TicketDto
import com.mrdor1stan.buonjourney.ui.entities.EventState
import com.mrdor1stan.buonjourney.ui.entities.TicketState
import com.mrdor1stan.buonjourney.ui.entities.map
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "TripsDetailsScreenViewModel"

data class EventsDetailsScreenUiState(val event: EventState?, val tickets: List<TicketState>)

class EventDetailsScreenViewModel(
    private val databaseRepository: DatabaseRepository,
    private val eventId: Long
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        EventsDetailsScreenUiState(null, listOf())
    )
    val uiState = _uiState.asStateFlow()

    fun addTicket(uri: Uri, mimeType: String?, displayName: String?) {
        viewModelScope.launch {
            databaseRepository.addTicket(
                TicketDto(
                    eventId = eventId,
                    uri = uri.toString(),
                    mimeType = mimeType,
                    displayName = displayName
                )
            )
        }
    }

    init {
        viewModelScope.launch {
            databaseRepository.getEvent(eventId).collect { event ->
                _uiState.value = uiState.value.copy(
                    event = event.event.map(),
                    tickets = event.tickets.map(TicketDto::map)
                )
            }
        }
    }

    companion object {
        fun Factory(eventId: Long): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BuonjourneyApplication)
                val databaseRepository: DatabaseRepository =
                    application.appContainer.databaseRepository
                EventDetailsScreenViewModel(
                    databaseRepository = databaseRepository,
                    eventId = eventId
                )
            }
        }
    }

}