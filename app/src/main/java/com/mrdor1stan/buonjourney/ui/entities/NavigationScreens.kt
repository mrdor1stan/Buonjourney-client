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
data class PackingListDetails(val id: Long)

@Serializable
data class WishlistPlaceDetails(val id: Long)

@Serializable
object AddTicket

@Serializable
class AddEvent(val tripId: Long)

@Serializable
class AddPackingList(val tripId: Long)

@Serializable
object Profile

@Serializable
object AllWishlistPlaces

@Serializable
object AddWishlistPlace
