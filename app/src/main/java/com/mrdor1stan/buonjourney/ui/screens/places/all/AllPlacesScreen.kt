package com.mrdor1stan.buonjourney.ui.screens.places.all

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.ui.common.ItemsListWithHeader
import com.mrdor1stan.buonjourney.ui.common.PlaceElement

@Composable
fun AllPlacesScreen(viewModel:AllPlacesScreenViewModel = viewModel(factory = AllPlacesScreenViewModel.Factory), modifier: Modifier = Modifier, navigateToAddScreen: () -> Unit, navigateToItem: (Long) -> Unit) {
    val state by viewModel.uiState.collectAsState()
    ItemsListWithHeader(header = "All places", items = state.results, navigateToAddScreen = navigateToAddScreen) { item, actions ->
        PlaceElement(item)
    }
}