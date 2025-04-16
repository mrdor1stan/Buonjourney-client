package com.mrdor1stan.buonjourney.ui.entities

import kotlinx.serialization.Serializable

@Serializable
object MainMenu

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
data class PackingListDetails(val id: Long)

@Serializable
data class WishlistCityDetails(val id: Long)

@Serializable
object AddTicket

@Serializable
class AddEvent(val tripId: Long)

@Serializable
class AddPackingList(val tripId: Long)

@Serializable
object Profile

@Serializable
object AllWishlistCities

@Serializable
object AddWishlistCity
