package com.mrdor1stan.buonjourney.ui.entities

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

data class TopLevelRoute<T>(val name: String, val route: T, val icon: ImageVector)

@Serializable
object MainMenu

@Serializable
object AllTrips

@Serializable
data class AddTrip(val id: Long?)

@Serializable
data class TripDetails(val id: Long)

@Serializable
data class AllTickets(val tripId: Long)

@Serializable
data class TicketDetails(val id: Long)

@Serializable
data class AllEvents(val tripId: Long?)

@Serializable
data class EventDetails(val id: Long)

@Serializable
object AddTicket

@Serializable
class AddEvent(val tripId: Long, val eventId: Long?)

@Serializable
object Profile
