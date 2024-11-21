package com.mrdor1stan.buonjourney.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrdor1stan.buonjourney.BuonjourneyApplication
import com.mrdor1stan.buonjourney.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LoginScreenUiState(
    val input: String,
    val isNextButtonEnabled: Boolean,
    val isLoaderShown: Boolean
)

class LoginScreenViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        LoginScreenUiState(input = "", isNextButtonEnabled = false, isLoaderShown = false)
    )

    val uiState = _uiState.asStateFlow()

    fun onInputChange(value: String) {
        _uiState.value = uiState.value.copy(
            input = value,
            isNextButtonEnabled = value.length > 2
            )
    }

    fun onContinueClick() {
        viewModelScope.launch {
            userRepository.logIn(name = uiState.value.input)
        }
        _uiState.value = uiState.value.copy(isLoaderShown = true)
    }

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BuonjourneyApplication)
                val userRepository: UserRepository =
                    application.appContainer.userRepository
                LoginScreenViewModel(
                    userRepository = userRepository
                )
            }
        }
    }

}