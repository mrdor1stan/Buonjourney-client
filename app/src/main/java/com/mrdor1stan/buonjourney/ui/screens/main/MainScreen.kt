package com.mrdor1stan.buonjourney.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.ui.common.ImageButton
import com.mrdor1stan.buonjourney.ui.entities.*

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = viewModel(factory = MainScreenViewModel.Factory),
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
    val navController = rememberNavController()
    val currentScreen by remember {
        mutableStateOf(navController.currentDestination)
    }
    val startDestination = MainMenu
    val showBackButton = currentScreen?.hasRoute<MainMenu>() ?: false

    Scaffold(modifier = modifier, topBar = {
        Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            ImageButton(
                iconRes = R.drawable.ic_profile,
                onClick = { navController.navigate(Profile) })
            if (showBackButton) {
                ImageButton(iconRes = R.drawable.ic_back, onClick = { navController.navigateUp() })
                ImageButton(
                    iconRes = R.drawable.ic_home,
                    onClick = { navController.navigate(startDestination) })
            }
            ImageButton(
                iconRes = R.drawable.ic_globe,
                onClick = { navController.navigate(AllWishlistPlaces) })
        }

    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<MainMenu> {



            }



        }

    }


}