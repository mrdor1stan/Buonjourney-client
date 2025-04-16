package com.mrdor1stan.buonjourney.ui.screens.places.newone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrdor1stan.buonjourney.BuonjourneyApplication
import com.mrdor1stan.buonjourney.data.DatabaseRepository
import com.mrdor1stan.buonjourney.data.db.CityDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AddCityScreenUiState(
    val title: String,
    val isAddButtonEnabled: Boolean,
)

class AddCityScreenViewModel(
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            AddCityScreenUiState(
                "",
                false,
            ),
        )
    val uiState = _uiState.asStateFlow()

    fun updateTitle(value: String) {
        _uiState.value = uiState.value.copy(title = value)
        validateAddButton()
    }

    fun validateAddButton() {
        _uiState.value =
            uiState.value.copy(
                isAddButtonEnabled = uiState.value.title.isNotEmpty(),
            )
    }

    suspend fun addCity() {
        databaseRepository.addCity(
            CityDto(name = uiState.value.title),
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = (this[APPLICATION_KEY] as BuonjourneyApplication)
                    val databaseRepository: DatabaseRepository =
                        application.appContainer.databaseRepository
                    AddCityScreenViewModel(
                        databaseRepository = databaseRepository,
                    )
                }
            }
    }
}
