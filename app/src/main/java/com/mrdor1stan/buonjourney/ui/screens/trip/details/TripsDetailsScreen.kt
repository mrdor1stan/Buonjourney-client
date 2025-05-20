package com.mrdor1stan.buonjourney.ui.screens.trip.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.ui.common.BodyText
import com.mrdor1stan.buonjourney.ui.common.Description
import com.mrdor1stan.buonjourney.ui.common.Headline
import com.mrdor1stan.buonjourney.ui.common.Loader
import com.mrdor1stan.buonjourney.ui.common.Title
import com.mrdor1stan.buonjourney.ui.entities.TripState
import com.mrdor1stan.buonjourney.ui.screens.event.all.AllEventsScreen
import com.mrdor1stan.buonjourney.ui.screens.packinglist.details.PackingListDetailsScreen
import kotlinx.coroutines.launch

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
    navigateToEditTripScreen: () -> Unit,
    navigateToAddEventScreen: (eventId: Long?) -> Unit,
    navigateToEventDetailsScreen: (Long) -> Unit,
    navigateBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    when (val trip = state.trip) {
        null -> Loader()
        else ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(modifier)
            ) {
                TripHeader(
                    trip = trip, onEditTrip = navigateToEditTripScreen, onDeleteTrip = {
                        scope.launch {
                            viewModel.deleteTrip()
                            navigateBack()
                        }
                    }, modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.small_margin))
                        .fillMaxWidth()
                )
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
                        0 -> AllEventsScreen(
                            tripId = tripId,
                            navigateToAddScreen = navigateToAddEventScreen,
                            navigateToItem = navigateToEventDetailsScreen
                        )

                        1 -> PackingListDetailsScreen(
                            tripId = tripId,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
    }
}

@Composable
fun TripHeader(
    trip: TripState,
    modifier: Modifier = Modifier,
    onEditTrip: () -> Unit,
    onDeleteTrip: () -> Unit
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_margin))
    ) {

        Headline(text = trip.title)
        trip.description?.let { Description(text = it) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(id = R.dimen.small_margin)
            ),
            modifier = Modifier.semantics(mergeDescendants = true) {}
        ) {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "Date",
                modifier = Modifier.size(24.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_margin))) {
                val date = "${trip.startDate ?: "..."} - ${trip.endDate ?: "..."}"
                BodyText(text = date)
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.middle_margin)),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onEditTrip, modifier = Modifier.weight(1f)) {
                Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_gap))) {
                    Icon(Icons.Default.Edit, null)
                    Text(text = "Edit trip")
                }
            }
            Button(onClick = onDeleteTrip, modifier = Modifier.weight(1f)) {
                Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_gap))) {
                    Icon(Icons.Default.Delete, null)
                    Text(text = "Delete trip")
                }
            }
        }


    }
}