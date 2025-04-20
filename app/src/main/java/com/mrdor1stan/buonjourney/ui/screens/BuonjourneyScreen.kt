package com.mrdor1stan.buonjourney.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.ui.screens.login.LoginScreen
import com.mrdor1stan.buonjourney.ui.screens.main.MainScreen
import com.mrdor1stan.buonjourney.ui.screens.splash.SplashScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BuonjourneyScreen(viewModel: BuonjourneyScreenViewModel = viewModel(factory = BuonjourneyScreenViewModel.Factory), modifier: Modifier = Modifier, sharedText: String?) {
    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()

    when (isUserLoggedIn) {
        true -> MainScreen(modifier = modifier, placeLink = sharedText)
        false -> LoginScreen(modifier = modifier.safeContentPadding())
        null -> SplashScreen(modifier)
    }





}