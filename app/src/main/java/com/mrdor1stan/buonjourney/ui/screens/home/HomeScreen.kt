package com.mrdor1stan.buonjourney.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.ui.common.EmptyListPlaceholder
import com.mrdor1stan.buonjourney.ui.common.Headline
import com.mrdor1stan.buonjourney.ui.common.Loader
import com.mrdor1stan.buonjourney.ui.common.Title

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = viewModel(
        factory = HomeScreenViewModel.Factory,
    ),
    navigateToTripDetailsScreen: (Long) -> Unit,
    navigateToEventDetailsScreen: (Long, Long) -> Unit,
    navigateToAddTripScreen: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    when (val trips = state.trips) {
        null -> Loader()
        else -> {
            when (trips.isNotEmpty()) {
                false -> EmptyListPlaceholder(
                    modifier = Modifier.fillMaxSize(),
                    navigateToAddScreen = navigateToAddTripScreen,
                    messageText = "No trips were created so far",
                    buttonText = "Create first trip"
                )

                true -> Column(
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .then(modifier)
                ) {
                    val tripsPagerState = rememberPagerState { trips.size }
                    LaunchedEffect(tripsPagerState, trips) {
                        snapshotFlow { tripsPagerState.currentPage }.collect { page ->
                            viewModel.loadTrip(page)
                        }
                    }
                    Headline(
                        text = "Home page",
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.middle_margin))
                    )
                    Title(text = "Trips", modifier = Modifier
                        .semantics { heading() }
                        .padding(dimensionResource(id = R.dimen.middle_margin))
                    )
                    HorizontalPager(
                        state = tripsPagerState,
                        contentPadding = PaddingValues(
                            start = dimensionResource(id = R.dimen.middle_margin),
                            end = dimensionResource(id = R.dimen.large_margin)
                        ),
                        modifier = Modifier.height(180.dp)
                    ) { page ->
                        val trip = trips[page]
                        Card(
                            onClick = { trip.id?.let { navigateToTripDetailsScreen(it) } },
                            modifier = Modifier.padding(dimensionResource(id = R.dimen.small_margin))
                        ) {
                            TripCard(
                                state = trip, modifier = Modifier
                                    .fillMaxSize()
                                    .padding(
                                        dimensionResource(id = R.dimen.middle_margin)
                                    )
                            )
                        }
                    }
                    Title(text = "Tickets", modifier = Modifier
                        .semantics { heading() }
                        .padding(dimensionResource(id = R.dimen.middle_margin)))

                    state.tickets.takeUnless { it.isNullOrEmpty() }?.let { tickets ->
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.middle_margin)),
                            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_margin))
                        ) {
                            items(tickets) {
                                Card {
                                    TicketCard(
                                        state = it, modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .size(width = 180.dp, height = 200.dp)
                                    )

                                }
                            }
                        }
                    } ?: Text(
                        text = "No tickets were added",
                        Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Title(text = "Events", modifier = Modifier
                        .semantics { heading() }
                        .padding(dimensionResource(id = R.dimen.middle_margin)))
                    state.events.takeUnless { it.isNullOrEmpty() }?.let { events ->
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = dimensionResource(id = R.dimen.middle_margin)),
                            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_margin))
                        ) {
                            items(events) {
                                Card(onClick = {
                                    val tripId = state.selectedTripIndex?.let { trips[it].id }
                                    tripId?.let { tripId ->
                                        navigateToEventDetailsScreen(tripId, it.id)
                                    }
                                }) {
                                    EventCard(
                                        state = it, modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .padding(dimensionResource(id = R.dimen.small_margin))
                                            .size(width = 164.dp, height = 100.dp)
                                    )
                                }
                            }
                        }
                    } ?: Text(
                        text = "No events were added",
                        Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

}