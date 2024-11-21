package com.mrdor1stan.buonjourney.ui.screens.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrdor1stan.buonjourney.BuonjourneyApplication
import com.mrdor1stan.buonjourney.data.UserRepository
import com.mrdor1stan.buonjourney.ui.screens.login.LoginScreenUiState
import com.mrdor1stan.buonjourney.ui.screens.login.LoginScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class MainScreenUiState(
    val name: String = "",
)

class MainScreenViewModel(
    private val userRepository: UserRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(
        MainScreenUiState()
    )

    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.name.collect { name ->
                _uiState.value = uiState.value.copy(name = name)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BuonjourneyApplication)
                val userRepository: UserRepository =
                    application.appContainer.userRepository
                MainScreenViewModel(
                    userRepository = userRepository
                )
            }
        }
    }

}