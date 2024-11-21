package com.mrdor1stan.buonjourney.ui.screens.event.newone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrdor1stan.buonjourney.BuonjourneyApplication
import com.mrdor1stan.buonjourney.data.DatabaseRepository
import com.mrdor1stan.buonjourney.data.db.PlaceDto
import com.mrdor1stan.buonjourney.data.db.EventDto
import com.mrdor1stan.buonjourney.ui.entities.EventState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

data class AddEventScreenUiState(
    val title: String,
    val description: String,
    val address: String,
    val dateTime: LocalDateTime?,
    val isAddButtonEnabled: Boolean
)


class AddEventScreenViewModel(
    private val tripId: Long,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        AddEventScreenUiState(
            "", "", "", null, false
        )
    )
    val uiState = _uiState.asStateFlow()

    fun updateDate(date: LocalDateTime) {
        _uiState.value = uiState.value.copy(dateTime = date)
        validateAddButton()
    }

    fun updateTitle(value: String) {
        _uiState.value = uiState.value.copy(title = value)
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

    fun validateAddButton() {
        _uiState.value = uiState.value.copy(
            isAddButtonEnabled =
            uiState.value.run {
                dateTime != null && title.isNotEmpty()
            }
        )
    }

    suspend fun addEvent() {
        databaseRepository.addEvent(
            with(uiState.value) {
                EventDto(
                    dateTime = dateTime!!,
                    title = title,
                    description = description,
                    address = address,
                    tripId = tripId
                )
            }
        )
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