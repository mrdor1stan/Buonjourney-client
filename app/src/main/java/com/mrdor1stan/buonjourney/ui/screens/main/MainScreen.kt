package com.mrdor1stan.buonjourney.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.ui.common.Headline
import com.mrdor1stan.buonjourney.ui.entities.AddEvent
import com.mrdor1stan.buonjourney.ui.entities.AddTrip
import com.mrdor1stan.buonjourney.ui.entities.AllEvents
import com.mrdor1stan.buonjourney.ui.entities.AllTickets
import com.mrdor1stan.buonjourney.ui.entities.AllTrips
import com.mrdor1stan.buonjourney.ui.entities.EventDetails
import com.mrdor1stan.buonjourney.ui.entities.MainMenu
import com.mrdor1stan.buonjourney.ui.entities.Profile
import com.mrdor1stan.buonjourney.ui.entities.TopLevelRoute
import com.mrdor1stan.buonjourney.ui.entities.TripDetails
import com.mrdor1stan.buonjourney.ui.screens.event.details.EventDetailsScreen
import com.mrdor1stan.buonjourney.ui.screens.event.newone.AddEventScreen
import com.mrdor1stan.buonjourney.ui.screens.profile.ProfileScreen
import com.mrdor1stan.buonjourney.ui.screens.trip.all.AllTripsScreen
import com.mrdor1stan.buonjourney.ui.screens.trip.details.TripsDetailsScreen
import com.mrdor1stan.buonjourney.ui.screens.trip.newone.AddTripScreen

private const val TAG = "MainScreen"

val TOP_LEVEL_ROUTES = listOf(
    TopLevelRoute(name = "Trips", route = AllTrips, icon = Icons.Default.Flight),
    TopLevelRoute(name = "Home", route = MainMenu, icon = Icons.Default.Home)
)

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val startDestination = MainMenu
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                TOP_LEVEL_ROUTES.forEach { topLevelRoute ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                topLevelRoute.icon,
                                contentDescription = topLevelRoute.name
                            )
                        },
                        label = { Text(topLevelRoute.name) },
                        selected = currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true,
                        onClick = {
                            navController.navigate(topLevelRoute.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                        }
                    )
                }
            }
        },
        topBar = {
            val showBackButton =
                TOP_LEVEL_ROUTES.none { currentDestination?.hasRoute(it.route::class) ?: false }
            if (showBackButton) {
                Column {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(
                            onClick = { navController.navigateUp() }) {
                            Image(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(
                                    id = R.string.back_label
                                )
                            )
                        }

                    }
                    HorizontalDivider()
                }
            }
        }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            composable<AllTrips> {
                AllTripsScreen(navigateToAddScreen = { id ->
                    navController.navigate(
                        AddTrip(
                            id
                        )
                    )
                },
                    navigateToItem = { id -> navController.navigate(TripDetails(id)) })
            }

            composable<MainMenu> {
                Headline(text = "Main menu")
            }

            composable<AddTrip> {
                val tripId = it.toRoute<AddTrip>().id
                AddTripScreen(tripId = tripId) {
                    navController.navigateUp()
                }
            }

            composable<Profile> {
                ProfileScreen(modifier = Modifier.fillMaxSize())
            }

            composable<TripDetails> {
                val tripId = it.toRoute<TripDetails>().id
                TripsDetailsScreen(tripId = tripId,
                    navigateToEventsListScreen = { navController.navigate(AllEvents(tripId)) },
                    navigateToTicketListScreen = { navController.navigate(AllTickets(tripId)) },
                    navigateToAddEventScreen = { eventId ->
                        navController.navigate(
                            AddEvent(
                                tripId = tripId,
                                eventId = eventId
                            )
                        )
                    },
                    navigateToEventDetailsScreen = { eventId ->
                        navController.navigate(EventDetails(eventId))
                    }
                )
            }

            composable<AddEvent> {
                val tripId = it.toRoute<AddEvent>().tripId
                val eventId = it.toRoute<AddEvent>().eventId
                AddEventScreen(
                    tripId = tripId,
                    eventId = eventId,
                    navigateBack = { navController.navigateUp() })
            }

            composable<EventDetails> {
                val eventId = it.toRoute<EventDetails>().eventId
                EventDetailsScreen(eventId = eventId, modifier = Modifier.fillMaxSize())
            }

        }
    }

}