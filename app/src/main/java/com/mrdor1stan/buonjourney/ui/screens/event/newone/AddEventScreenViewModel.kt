package com.mrdor1stan.buonjourney.ui.screens.event.newone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrdor1stan.buonjourney.BuonjourneyApplication
import com.mrdor1stan.buonjourney.data.DatabaseRepository
import com.mrdor1stan.buonjourney.data.db.EventDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime
import java.time.LocalTime

data class AddEventScreenUiState(
    val title: String,
    val description: String,
    val eventType: EventDto.Type,
    val address: String,
    val isAddButtonEnabled: Boolean,
    val startDate: LocalDateTime?,
    val startTimeFrom: LocalTime?,
    val startTimeTo: LocalTime?,
    val endDate: LocalDateTime?,
    val endTimeFrom: LocalTime?,
    val endTimeTo: LocalTime?,
)

class AddEventScreenViewModel(
    private val tripId: Long,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        AddEventScreenUiState(
            "", "", EventDto.Type.NoType, "", false,
            null, null, null, null, null, null
        )
    )
    val uiState = _uiState.asStateFlow()

    fun updateTitle(value: String) {
        _uiState.value = uiState.value.copy(title = value)
        validateAddButton()
    }

    fun updateEventType(type: EventDto.Type) {
        _uiState.value = uiState.value.copy(
            eventType = type,
            startDate = null,
            startTimeFrom = null,
            startTimeTo = null,
            endDate = null,
            endTimeFrom = null,
            endTimeTo = null,
        )
        validateAddButton()
    }

    fun updateDescription(value: String) {
        _uiState.value = uiState.value.copy(description = value)
        validateAddButton()
    }

    fun updateAddress(value: String) {
        _uiState.value = uiState.value.copy(address = value)
        validateAddButton()
    }

    fun updateStartDate(value: LocalDateTime?) {
        _uiState.value = uiState.value.copy(startDate = value)
        validateAddButton()
    }

    fun updateStartTimeFrom(value: LocalTime?) {
        _uiState.value = uiState.value.copy(startTimeFrom = value)
        validateAddButton()
    }

    fun updateStartTimeTo(value: LocalTime?) {
        _uiState.value = uiState.value.copy(startTimeTo = value)
        validateAddButton()
    }

    fun updateEndDate(value: LocalDateTime?) {
        _uiState.value = uiState.value.copy(endDate = value)
        validateAddButton()
    }

    fun updateEndTimeFrom(value: LocalTime?) {
        _uiState.value = uiState.value.copy(endTimeFrom = value)
        validateAddButton()
    }

    fun updateEndTimeTo(value: LocalTime?) {
        _uiState.value = uiState.value.copy(endTimeTo = value)
        validateAddButton()
    }

    fun validateAddButton() {
        _uiState.value = uiState.value.copy(
            isAddButtonEnabled =
            uiState.value.run {
                title.isNotEmpty()
            }
        )
    }

    suspend fun addEvent() {

        with(uiState.value) {
            val payload = when (uiState.value.eventType) {
                EventDto.Type.Accommodation -> EventDto.Payload.AccommodationData(
                    checkInDate = startDate,
                    checkInTimeFrom = startTimeFrom,
                    checkInTimeTo = startTimeTo,
                    checkOutDate = endDate,
                    checkOutTimeFrom = endTimeFrom,
                    checkOutTimeTo = endTimeTo
                )

                EventDto.Type.Transport -> EventDto.Payload.TransportData(
                    departureDate = startDate,
                    departureTime = startTimeFrom,
                    arrivalDate = endDate,
                    arrivalTime = endTimeFrom
                )

                EventDto.Type.Entertainment -> EventDto.Payload.EntertainmentData(
                    startDate = startDate,
                    endDate = endDate,
                    startTime = startTimeFrom,
                    endTime = endTimeFrom
                )

                EventDto.Type.NoType -> EventDto.Payload.NoData
            }

            databaseRepository.addEvent(
                EventDto(
                    payload = payload,
                    title = title,
                    description = description,
                    address = address,
                    tripId = tripId
                )
        )
        }
    }

    companion object {
        fun Factory(tripId: Long): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BuonjourneyApplication)
                val databaseRepository: DatabaseRepository =
                    application.appContainer.databaseRepository
                AddEventScreenViewModel(
                    tripId = tripId,
                    databaseRepository = databaseRepository
                )
            }
        }
    }

}