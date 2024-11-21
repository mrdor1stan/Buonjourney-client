package com.mrdor1stan.buonjourney.ui.entities

import kotlinx.serialization.Serializable

@Serializable
object MainMenu

@Serializable
object AddTrip

@Serializable
data class TripDetails(val id: Long)

@Serializable
object AllTickets

@Serializable
data class TicketDetails(val id: Long)

@Serializable
object AllEvents

@Serializable
data class EventDetails(val id: Long)

@Serializable
object AllPackingLists

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
