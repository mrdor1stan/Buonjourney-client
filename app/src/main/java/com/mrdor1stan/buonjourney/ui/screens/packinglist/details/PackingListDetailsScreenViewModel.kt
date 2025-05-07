package com.mrdor1stan.buonjourney.ui.screens.packinglist.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrdor1stan.buonjourney.BuonjourneyApplication
import com.mrdor1stan.buonjourney.data.DatabaseRepository
import com.mrdor1stan.buonjourney.data.db.PackingListNodeDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PackingListDetailsScreenUiState(
    val packingList: List<PackingListNodeDto>?,
    val input: String,
    val isAddButtonEnabled: Boolean,
    val selectedListScreenMode: PackingListScreenMode,
    val editedItemIndex: Int?
)

class PackingListDetailsScreenViewModel(
    private val databaseRepository: DatabaseRepository,
    private val tripId: Long,
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            PackingListDetailsScreenUiState(
                packingList = null,
                input = "",
                isAddButtonEnabled = false,
                selectedListScreenMode = PackingListScreenMode.Check,
                editedItemIndex = null
            ),
        )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            databaseRepository.getTrip(tripId).collect { trip ->
                _uiState.value =
                    uiState.value.copy(
                        packingList = trip.packingItems.sortedBy { it.ordinal }
                    )
            }
        }
    }

    fun onItemPacked(item: PackingListNodeDto) {
        viewModelScope.launch {
            databaseRepository.updatePackingItem(item.copy(isPacked = item.isPacked.not()))
        }
    }

    fun onItemMoved(index: Int, moveUpward: Boolean) {
        val item1 = uiState.value.packingList?.get(index)
        val item2 = uiState.value.packingList?.get(if (moveUpward) index - 1 else index + 1)
        if (item1 != null && item2 != null) {
            viewModelScope.launch {
                databaseRepository.swapItemsOrder(item1, item2)
            }
        }
    }

    fun onEditItem(index: Int?) {
        _uiState.value = uiState.value.copy(editedItemIndex = index)
    }

    fun onItemRenamed(item: PackingListNodeDto, newName: String) {
        viewModelScope.launch {
            databaseRepository.updatePackingItem(item.copy(name = newName))
            onEditItem(null)
        }
    }

    fun onItemRemoved(item: PackingListNodeDto) {
        viewModelScope.launch {
            databaseRepository.deletePackingItem(item)
        }
    }

    suspend fun addItemToList(itemType: PackingListNodeDto.Type) {
        databaseRepository.addPackingItem(
            PackingListNodeDto(
                name = uiState.value.input,
                isPacked = false,
                tripId = tripId,
                nodeType = itemType,
                ordinal = uiState.value.packingList?.size ?: 0
            ),
        )
        updateInput("")
    }

    fun updateInput(value: String) {
        _uiState.value = uiState.value.copy(input = value, isAddButtonEnabled = value.isNotEmpty())
    }

    fun selectListMode(mode: PackingListScreenMode) {
        _uiState.value = uiState.value.copy(selectedListScreenMode = mode, editedItemIndex = null)
    }

    companion object {
        fun Factory(tripId: Long): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = (this[APPLICATION_KEY] as BuonjourneyApplication)
                    val databaseRepository: DatabaseRepository =
                        application.appContainer.databaseRepository
                    PackingListDetailsScreenViewModel(
                        databaseRepository = databaseRepository,
                        tripId = tripId,
                    )
                }
            }
    }
}
