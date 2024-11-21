package com.mrdor1stan.buonjourney.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "trips")
data class TripDto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val title: String,
    val placeId: Long,
    val status: TripStatus
) {
    enum class TripStatus {
        PLANNED,
        ONGOING,
        COMPLETED
    }
}

@Entity(tableName = "tickets")
data class TicketDto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fileUri: String,
    val dateTime: LocalDateTime,
    val caption: String,
    val isUsed: Boolean,
    val ticketType: TicketType,
    val tripId: Long
) {
    enum class TicketType {
        FLIGHT,
        BUS,
        TRAIN,
        FERRY,
        ENTERTAINMENT,
        OTHER,
        NO_TYPE
    }

}

@Entity(tableName = "places")
data class PlaceDto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
)

@Entity(tableName = "packinglists")
data class PackingListDto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val tripId: Long
)

@Entity(tableName = "packingitems")
data class PackingItemDto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val isPacked: Boolean,
    val listId: Long
)

@Entity(tableName = "events")
data class EventDto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val dateTime: LocalDateTime,
    val description: String,
    val address: String,
    val tripId: Long
)