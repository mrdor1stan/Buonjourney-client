package com.mrdor1stan.buonjourney.ui.screens.trip.all

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.ui.common.ListWithHeader
import com.mrdor1stan.buonjourney.ui.common.TripElement

@Composable
fun AllTripsScreen(
    viewModel: AllTripsScreenViewModel = viewModel(factory = AllTripsScreenViewModel.Factory),
    modifier: Modifier = Modifier,
    navigateToAddScreen: () -> Unit,
    navigateToItem: (Long) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    ListWithHeader(
        header = "All trips",
        modifier = modifier,
        items = state.results,
        navigateToAddScreen = navigateToAddScreen,
    ) { item ->
        TripElement(item, Modifier.padding(16.dp).clickable { item.id?.let { navigateToItem(it) } })
    }
}