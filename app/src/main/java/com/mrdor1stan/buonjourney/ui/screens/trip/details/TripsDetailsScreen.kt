package com.mrdor1stan.buonjourney.ui.screens.trip.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.ui.common.ListWithHeader
import com.mrdor1stan.buonjourney.ui.common.Loader
import com.mrdor1stan.buonjourney.ui.common.PrimaryButton
import com.mrdor1stan.buonjourney.ui.common.TripElement

@Composable
fun TripsDetailsScreen(
    tripId: Long,
    viewModel: TripsDetailsScreenViewModel = viewModel(
        factory = TripsDetailsScreenViewModel.Factory(
            tripId
        )
    ),
    modifier: Modifier = Modifier,
    navigateToPackingListScreen: () -> Unit,
    navigateToEventsListScreen: () -> Unit,
    navigateToTicketListScreen: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    when (val trip = state.trip) {
        null -> Loader()
        else ->
            Column {
                TripElement(trip)
                Row {
                    PrimaryButton(
                        text = "Events",
                        onClick = navigateToEventsListScreen,
                        enabled = true
                    )
                    PrimaryButton(
                        text = "Packing lists",
                        onClick = navigateToPackingListScreen,
                        enabled = true
                    )
//                PrimaryButton(
//                    text = "Tickets",
//                    onClick = navigateToTicketListScreen,
//                    enabled = true
//                )
                }
            }
    }

}