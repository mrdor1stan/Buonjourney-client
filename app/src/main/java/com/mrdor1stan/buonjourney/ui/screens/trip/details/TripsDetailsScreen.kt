package com.mrdor1stan.buonjourney.ui.screens.trip.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.ui.common.EventElement
import com.mrdor1stan.buonjourney.ui.common.Headline
import com.mrdor1stan.buonjourney.ui.common.Loader
import com.mrdor1stan.buonjourney.ui.common.Title
import com.mrdor1stan.buonjourney.ui.entities.EventState
import com.mrdor1stan.buonjourney.ui.screens.event.all.AllEventsScreen
import com.mrdor1stan.buonjourney.ui.screens.packinglist.details.PackingListDetailsScreen

enum class TripsDetailsScreenTabs {
    Events, PackingList
}

@Composable
fun TripsDetailsScreenTabs.titleResId() = when (this) {
    TripsDetailsScreenTabs.Events -> (R.string.schedule_title)
    TripsDetailsScreenTabs.PackingList -> (R.string.packing_list_title)
}


@Composable
fun TripsDetailsScreen(
    tripId: Long,
    viewModel: TripsDetailsScreenViewModel = viewModel(
        factory = TripsDetailsScreenViewModel.Factory(
            tripId
        )
    ),
    modifier: Modifier = Modifier,
    navigateToEventsListScreen: () -> Unit,
    navigateToAddEventScreen: (eventId: Long?) -> Unit,
    navigateToTicketListScreen: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    when (val trip = state.trip) {
        null -> Loader()
        else ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(modifier)
            ) {
                var tabIndex by remember { mutableIntStateOf(0) }

                TabRow(selectedTabIndex = tabIndex, modifier = Modifier.fillMaxWidth()) {
                    TripsDetailsScreenTabs.entries.forEachIndexed { index, tab ->
                        Tab(text = { Title(stringResource(id = tab.titleResId())) },
                            selected = tabIndex == index,
                            onClick = { tabIndex = index }
                        )
                    }
                }
                state.trip?.id?.let { tripId ->
                    when (tabIndex) {
                        0 -> AllEventsScreen(tripId = tripId, navigateToAddScreen = { navigateToAddEventScreen(it) })
                        1 -> PackingListDetailsScreen(
                            tripId = tripId,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

    }
}
