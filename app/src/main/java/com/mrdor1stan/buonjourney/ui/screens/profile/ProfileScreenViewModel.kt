package com.mrdor1stan.buonjourney.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mrdor1stan.buonjourney.BuonjourneyApplication
import com.mrdor1stan.buonjourney.data.DatabaseRepository
import com.mrdor1stan.buonjourney.data.UserRepository
import com.mrdor1stan.buonjourney.ui.screens.login.LoginScreenUiState
import com.mrdor1stan.buonjourney.ui.screens.login.LoginScreenViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ProfileScreenUiState(
    val isLoaderShown: Boolean,
    val name: String
)

class ProfileScreenViewModel(
    private val databaseRepository: DatabaseRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProfileScreenUiState(isLoaderShown = false, name = "")
    )
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.name.collect { name ->
                _uiState.value = uiState.value.copy(name = name)
            }
        }
    }


    suspend fun onLogOutClick() {
            userRepository.logOut()
       }

    suspend fun eraseDatabase(){
        databaseRepository.eraseDatabase()
    }

    fun showLoader(){
        _uiState.value = uiState.value.copy(isLoaderShown = true)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BuonjourneyApplication)
                val userRepository: UserRepository =
                    application.appContainer.userRepository
                val databaseRepository: DatabaseRepository =
                    application.appContainer.databaseRepository
                ProfileScreenViewModel(
                    databaseRepository = databaseRepository,
                    userRepository = userRepository
                )
            }
        }
    }

}