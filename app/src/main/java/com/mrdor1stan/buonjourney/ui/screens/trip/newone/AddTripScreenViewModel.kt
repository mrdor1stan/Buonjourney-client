package com.mrdor1stan.buonjourney.ui.screens.trip.newone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrdor1stan.buonjourney.BuonjourneyApplication
import com.mrdor1stan.buonjourney.data.DatabaseRepository
import com.mrdor1stan.buonjourney.data.db.PlaceDto
import com.mrdor1stan.buonjourney.data.db.TripDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

data class AddTripScreenUiState(
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
    val title: String,
    val destination: PlaceDto?,
    val status: TripDto.TripStatus,
    val isAddButtonEnabled: Boolean,
    val allPlaces: List<PlaceDto>
)

class AddTripScreenViewModel(
    val tripId: Long?,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<AddTripScreenUiState> =
        MutableStateFlow(
            AddTripScreenUiState(
                null, null, "", null,
                TripDto.TripStatus.PLANNED, false, listOf()
            )
        )

    init {
        viewModelScope.launch {
            if (tripId != null) {
                databaseRepository.getTrip(tripId)?.collect {
                    _uiState.value = uiState.value.copy(
                        startDate = it.trip.startDate,
                        endDate = it.trip.endDate,
                        title = it.trip.title,
                        destination = it.place,
                        status = it.trip.status
                    )
                }
            }
            databaseRepository.getPlaces().collect { places ->
                _uiState.value = uiState.value.copy(allPlaces = places)
            }
        }
    }

    val uiState = _uiState.asStateFlow()


    fun updateStartDate(date: LocalDateTime) {
        _uiState.value = uiState.value.copy(startDate = date)
        validateAddButton()
    }

    fun updateEndDate(date: LocalDateTime) {
        _uiState.value = uiState.value.copy(endDate = date)
        validateAddButton()
    }

    fun updateTitle(value: String) {
        _uiState.value = uiState.value.copy(title = value)
        validateAddButton()
    }

    fun updateDestination(value: PlaceDto) {
        _uiState.value = uiState.value.copy(destination = value)
        validateAddButton()
    }

    fun updateStatus(status: TripDto.TripStatus) {
        _uiState.value = uiState.value.copy(status = status)
        validateAddButton()
    }

    private fun validateAddButton() {
        _uiState.value = uiState.value.copy(
            isAddButtonEnabled =
            uiState.value.run {
                startDate != null && endDate != null && startDate < endDate && title.isNotEmpty()
                        && destination != null
            }
        )
    }

    suspend fun addTrip() {
        databaseRepository.addTrip(
            with(uiState.value) {
                TripDto(
                    startDate = startDate!!,
                    endDate = endDate!!,
                    title = title,
                    placeId = destination!!.id,
                    status = status
                )
            }
        )
    }

    companion object {
        fun Factory(tripId: Long?): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BuonjourneyApplication)
                val databaseRepository: DatabaseRepository =
                    application.appContainer.databaseRepository
                AddTripScreenViewModel(
                    tripId = tripId,
                    databaseRepository = databaseRepository
                )
            }
        }
    }

}