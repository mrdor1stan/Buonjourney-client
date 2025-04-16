package com.mrdor1stan.buonjourney.ui.screens.trip.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.data.db.EventDto
import com.mrdor1stan.buonjourney.data.db.PackingListDto
import com.mrdor1stan.buonjourney.ui.common.EventElement
import com.mrdor1stan.buonjourney.ui.common.Loader
import com.mrdor1stan.buonjourney.ui.common.PackingList
import com.mrdor1stan.buonjourney.ui.entities.EventState
import com.mrdor1stan.buonjourney.ui.entities.PackingListState
import com.mrdor1stan.buonjourney.ui.entities.CityState

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
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    when (val trip = state.trip) {
        null -> Loader()
        else ->
            Column(modifier = Modifier
                .fillMaxWidth()
                .then(modifier)) {
                var tabIndex by remember { mutableIntStateOf(0) }
                val tabs = listOf("Schedule", "Packing list", "Desires")
                ScrollableTabRow(selectedTabIndex = tabIndex) {
                    tabs.forEachIndexed { index, title ->
                        Tab(text = { Text(title) },
                            selected = tabIndex == index,
                            onClick = { tabIndex = index }
                        )
                    }
                }
                when (tabIndex) {
                    0 -> TripEventsTab(state.trip?.events?: listOf())
                    1 -> state.trip?.packingList?.let { TripPackingListTab(it) }
                  //  2-> TripDesiresTab(state.trip?.destination)
                }

            }
    }
}

@Composable
fun TripDesiresTab(destinations: List<CityState>) {

}

@Composable
fun TripPackingListTab(packingList: PackingListState) {
    PackingList(packingList)
}

@Composable
fun TripEventsTab(events: List<EventState>) {
    LazyColumn {
        items(events) {
            EventElement(it)
        }
    }
}
