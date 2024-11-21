package com.mrdor1stan.buonjourney.ui.screens.trip.all

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrdor1stan.buonjourney.BuonjourneyApplication
import com.mrdor1stan.buonjourney.data.DatabaseRepository
import com.mrdor1stan.buonjourney.ui.entities.PackingListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AllPackingListsScreenUiState(
    val results: List<PackingListState>
)

class AllPackingListsScreenViewModel(
    private val tripId: Long,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AllPackingListsScreenUiState(results = listOf())
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            databaseRepository.getPackingListsByTrip(tripId).collect { lists ->
                _uiState.value = uiState.value.copy(results = lists.map {
                    PackingListState(it.list.name, it.items, it.list.id)
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
                AllPackingListsScreenViewModel(
                    tripId = tripId,
                    databaseRepository = databaseRepository
                )
            }
        }
    }

}