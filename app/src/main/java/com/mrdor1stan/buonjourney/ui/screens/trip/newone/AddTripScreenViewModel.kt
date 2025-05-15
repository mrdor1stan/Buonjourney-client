package com.mrdor1stan.buonjourney.ui.screens.trip.newone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrdor1stan.buonjourney.BuonjourneyApplication
import com.mrdor1stan.buonjourney.data.DatabaseRepository
import com.mrdor1stan.buonjourney.data.db.TripDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

data class AddTripScreenUiState(
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
    val title: String,
    val description: String?,
    val isAddButtonEnabled: Boolean
)

class AddTripScreenViewModel(
    val tripId: Long?,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<AddTripScreenUiState> =
        MutableStateFlow(
            AddTripScreenUiState(
                null, null, "", null, false
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
                        description = it.trip.description,
                        isAddButtonEnabled = true
                    )
                }
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

    fun updateDescription(value: String) {
        _uiState.value = uiState.value.copy(description = value.takeIf { it.isNotEmpty() })
        validateAddButton()
    }

    private fun validateAddButton() {
        _uiState.value = uiState.value.copy(
            isAddButtonEnabled =
            uiState.value.run {
                title.isNotEmpty()
            }
        )
    }

    suspend fun saveTrip() {
        val tripDto = with(uiState.value) {
            TripDto(
                startDate = startDate,
                endDate = endDate,
                title = title,
                description = description
            )
        }

        tripId?.let {
            databaseRepository.updateTrip(
                tripDto.copy(id = it)
            )
        } ?: databaseRepository.addTrip(tripDto)
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