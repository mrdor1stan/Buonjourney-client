package com.mrdor1stan.buonjourney.ui.screens.event.all

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.ui.common.EventsList

@Composable
fun AllEventsScreen(
    tripId: Long?,
    viewModel: AllEventsScreenViewModel = viewModel(
        factory = AllEventsScreenViewModel.Factory(
            tripId
        )
    ),
    modifier: Modifier = Modifier,
    navigateToAddScreen: (() -> Unit)?
) {
    val state by viewModel.uiState.collectAsState()
    EventsList(
        events = state.results,
        navigateToAddScreen = navigateToAddScreen,
        modifier = modifier
    )
}