package com.mrdor1stan.buonjourney.ui.screens.trip.all

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.ui.common.BodyText
import com.mrdor1stan.buonjourney.ui.common.Headline
import com.mrdor1stan.buonjourney.ui.common.ListHeader
import com.mrdor1stan.buonjourney.ui.common.ListWithHeader
import com.mrdor1stan.buonjourney.ui.common.PlaceElement
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
        TripElement(item)
    }
}