package com.mrdor1stan.buonjourney.ui.screens.packinglist.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrdor1stan.buonjourney.BuonjourneyApplication
import com.mrdor1stan.buonjourney.data.DatabaseRepository
import com.mrdor1stan.buonjourney.data.db.PackingListItemDto
import com.mrdor1stan.buonjourney.ui.entities.PackingListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PackingListDetailsScreenUiState(
    val packingList: PackingListState?,
    val input: String,
    val isAddButtonEnabled: Boolean,
)

class PackingListDetailsScreenViewModel(
    private val databaseRepository: DatabaseRepository,
    private val listId: Long,
) : ViewModel() {
    suspend fun updateItem(item: PackingListItemDto) {
        databaseRepository.updatePackingItem(item.copy(isPacked = item.isPacked.not()))
    }

    suspend fun addItemToList() {
        databaseRepository.addPackingItem(
            PackingListItemDto(
                name = uiState.value.input,
                isPacked = false,
                listId = listId,
            ),
        )
    }

    fun updateInput(value: String) {
        _uiState.value = uiState.value.copy(input = value, isAddButtonEnabled = value.isNotEmpty())
    }

    private val _uiState =
        MutableStateFlow(
            PackingListDetailsScreenUiState(packingList = null, input = "", isAddButtonEnabled = false),
        )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            databaseRepository.getPackingList(listId).collect { list ->
                _uiState.value =
                    uiState.value.copy(
                        packingList =
                            list.let {
                                PackingListState(
                                    name = it.list.name,
                                    items = it.items,
                                )
                            },
                    )
            }
        }
    }

    companion object {
        fun Factory(listId: Long): ViewModelProvider.Factory =
            viewModelFactory {
                initializer {
                    val application = (this[APPLICATION_KEY] as BuonjourneyApplication)
                    val databaseRepository: DatabaseRepository =
                        application.appContainer.databaseRepository
                    PackingListDetailsScreenViewModel(
                        databaseRepository = databaseRepository,
                        listId = listId,
                    )
                }
            }
    }
}
