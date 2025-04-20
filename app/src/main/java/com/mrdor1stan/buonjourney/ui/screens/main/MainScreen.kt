package com.mrdor1stan.buonjourney.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.ui.common.ImageButton
import com.mrdor1stan.buonjourney.ui.entities.*
import com.mrdor1stan.buonjourney.ui.screens.event.all.AllEventsScreen
import com.mrdor1stan.buonjourney.ui.screens.event.newone.AddEventScreen
import com.mrdor1stan.buonjourney.ui.screens.packinglist.details.PackingListDetailsScreen
import com.mrdor1stan.buonjourney.ui.screens.packinglist.newone.AddPackingListScreen
import com.mrdor1stan.buonjourney.ui.screens.places.all.AllCitiesScreen
import com.mrdor1stan.buonjourney.ui.screens.places.newone.AddCityScreen
import com.mrdor1stan.buonjourney.ui.screens.profile.ProfileScreen
import com.mrdor1stan.buonjourney.ui.screens.trip.all.AllTripsScreen
import com.mrdor1stan.buonjourney.ui.screens.trip.details.TripsDetailsScreen
import com.mrdor1stan.buonjourney.ui.screens.trip.newone.AddTripScreen


inline fun <reified T : Any> shouldShowButton(currentScreen: NavBackStackEntry?) =
    currentScreen?.destination?.let { currentDestination ->
        !currentDestination.hasRoute(T::class)
    }

@Composable
fun MainScreen(modifier: Modifier = Modifier, placeLink: String?) {

    val navController = rememberNavController()
    val currentScreen by navController.currentBackStackEntryAsState()
    val startDestination = MainMenu

    if (placeLink != null)
        navController.navigate(AddWishlistCity(placeLink = placeLink, id = null))


    val showBackButton = shouldShowButton<MainMenu>(currentScreen) ?: false
    val showProfileButton = shouldShowButton<Profile>(currentScreen) ?: false
    val showCitiesButton = shouldShowButton<AllWishlistCities>(currentScreen) ?: false
    val showEventsButton = shouldShowButton<AllEvents>(currentScreen) ?: false

    Scaffold(modifier = modifier, topBar = {
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (showProfileButton) ImageButton(iconRes = R.drawable.ic_profile,
                    onClick = { navController.navigate(Profile) })
                if (showBackButton) {
                    ImageButton(iconRes = R.drawable.ic_back,
                        onClick = { navController.navigateUp() })
                    ImageButton(iconRes = R.drawable.ic_home,
                        onClick = { navController.navigate(startDestination) })
                }
                if (showEventsButton) ImageButton(iconRes = R.drawable.ic_event,
                    onClick = { navController.navigate(AllEvents(null)) })
                if (showCitiesButton) ImageButton(
                    iconRes = R.drawable.ic_globe,
                    onClick = { navController.navigate(AllWishlistCities) })
            }
            HorizontalDivider()
        }

    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<MainMenu> {
                AllTripsScreen(navigateToAddScreen = { id -> navController.navigate(AddTrip(id)) },
                    navigateToItem = { id -> navController.navigate(TripDetails(id)) })
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

            composable<AllWishlistCities> {
                AllCitiesScreen(navigateToAddScreen = { navController.navigate(AddWishlistCity(null, null)) },
                    navigateToItem = { navController.navigate(TripDetails(it)) })
            }

            composable<AddWishlistCity> {
                val placeLink = it.toRoute<AddWishlistCity>().placeLink
                val cityId = it.toRoute<AddWishlistCity>().id
                AddCityScreen(placeLink = placeLink, cityId = cityId, modifier = Modifier.fillMaxSize(), navigateBack = {
                    navController.navigateUp()
                })
            }

            composable<TripDetails> {
                val tripId = it.toRoute<TripDetails>().id
                TripsDetailsScreen(tripId = tripId,
                    navigateToPackingListScreen = { //navController.navigate(AllPackingLists(tripId))
                         },
                    navigateToEventsListScreen = { navController.navigate(AllEvents(tripId)) },
                    navigateToTicketListScreen = { navController.navigate(AllTickets(tripId)) })
            }

            composable<PackingListDetails> {
                val listId = it.toRoute<PackingListDetails>().id
                PackingListDetailsScreen(listId)
            }
            composable<AllEvents> {
                val tripId = it.toRoute<AllEvents>().tripId
                AllEventsScreen(tripId,
                    navigateToAddScreen = tripId?.let { { navController.navigate(AddEvent(tripId)) } })
            }
            composable<AddEvent> {
                val tripId = it.toRoute<AddEvent>().tripId
                AddEventScreen(tripId = tripId, navigateBack = { navController.navigateUp() })
            }
            composable<AddPackingList> {
                val tripId = it.toRoute<AddPackingList>().tripId
                AddPackingListScreen(tripId = tripId,
                    modifier = Modifier.fillMaxSize(),
                    navigateBack = {
                        navController.navigateUp()
                    })
            }

        }
    }

}