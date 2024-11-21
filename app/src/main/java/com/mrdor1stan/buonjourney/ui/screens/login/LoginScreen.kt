package com.mrdor1stan.buonjourney.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.ui.common.BodyText
import com.mrdor1stan.buonjourney.ui.common.Headline

@Composable
fun LoginScreen(viewModel: LoginScreenViewModel = viewModel(factory = LoginScreenViewModel.Factory), modifier: Modifier = Modifier) {
    val state by viewModel.uiState.collectAsState()

    Box(modifier = modifier) {
        if (state.isLoaderShown)
            CircularProgressIndicator(modifier = Modifier.background(color = Color.DarkGray.copy(alpha = 0.2f)).fillMaxSize().align(Alignment.Center))

        Column (Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Headline(text = "Welcome to Buonjourney!")
            BodyText(text = "What's your name, dear traveller?")
            TextField(value = state.input, onValueChange = viewModel::onInputChange)
            Button(onClick = viewModel::onContinueClick, enabled = state.isNextButtonEnabled) {
                BodyText(text = "Continue")
            }
        }
    }
}