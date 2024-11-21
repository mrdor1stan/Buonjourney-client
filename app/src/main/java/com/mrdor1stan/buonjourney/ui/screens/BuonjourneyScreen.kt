package com.mrdor1stan.buonjourney.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Popup
import androidx.lifecycle.ViewModel
import com.mrdor1stan.buonjourney.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.ui.common.Headline
import com.mrdor1stan.buonjourney.ui.screens.login.LoginScreen
import com.mrdor1stan.buonjourney.ui.screens.main.MainScreen
import com.mrdor1stan.buonjourney.ui.screens.splash.SplashScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BuonjourneyScreen(viewModel: BuonjourneyScreenViewModel = viewModel(factory = BuonjourneyScreenViewModel.Factory), modifier: Modifier = Modifier) {
    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()

    when (isUserLoggedIn) {
        true -> MainScreen(modifier = modifier)
        false -> LoginScreen(modifier = modifier.safeContentPadding())
        null -> SplashScreen(modifier)
    }





}