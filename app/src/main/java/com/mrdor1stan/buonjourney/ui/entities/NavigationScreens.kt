package com.mrdor1stan.buonjourney.ui.entities

import kotlinx.serialization.Serializable

@Serializable
object MainMenu

@Serializable
object AddTrip

@Serializable
data class TripDetails(val id: Long)

@Serializable
data class AllTickets(val tripId: Long)

@Serializable
data class TicketDetails(val id: Long)

@Serializable
data class AllEvents(val tripId: Long)

@Serializable
data class EventDetails(val id: Long)

@Serializable
data class AllPackingLists(val tripId: Long)

@Serializable
data class PackingListDetails(val id: Int)

@Serializable
data class WishlistPlaceDetails(val id: Int)

@Serializable
object AddTicket

@Serializable
object AddEvent

@Serializable
object AddPackingList

@Serializable
object Profile

@Serializable
object AllWishlistPlaces

@Serializable
object AddWishlistPlace
