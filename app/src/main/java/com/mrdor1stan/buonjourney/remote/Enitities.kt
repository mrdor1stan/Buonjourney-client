package com.mrdor1stan.buonjourney.remote

import com.mrdor1stan.buonjourney.data.db.TicketDto
import com.mrdor1stan.buonjourney.data.db.TripDto


data class UserEntity(
val id: Long,
    val username: String,
    val name: String,
    val password: String,
    val email: String,
    val placesToVisit: List<PlaceEntity>,
    val trips: List<TripEntity>
)

data class TripEntity(
    val id: Long,
    val startDate: String,
    val endDate: String,
    val title: String,
    val destination: String,
    val status: TripDto.TripStatus,
    val packingLists: List<PackingListEntity>,
    val events: List<EventEntity>,
    val tickets: List<TicketEntity>,
    val userId: Long
)

data class TicketEntity(
    val id: Long,
    val fileUrl: String,
    val dateTime: String,
    val caption: String,
    val isUsed: String,
    val ticketType: TicketDto.TicketType,
    val tripEntity: TripEntity
)

data class PlaceEntity(
    val id: Long,
    val name: String,
    val address: String,
    val description: String,
    val userId: Long
)

data class PackingListEntity(
    val id: Long,
    val name: String,
    val items: List<PackingItemEntity>,
    val tripEntity: TripEntity
)

data class PackingItemEntity(
    val id: Long,
    val name: String,
    val isPacked: Boolean,
    val packingListEntity: PackingListEntity
)

data class EventEntity(
    val id: Long,
    val title: String,
    val dateTime: String,
    val description: String,
    val address: String,
    val tripEntity: TripEntity
)