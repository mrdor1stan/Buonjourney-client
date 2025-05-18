package com.mrdor1stan.buonjourney.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(
    tableName = "trips"
)
data class TripDto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
    val description: String?
)

@Entity(
    tableName = "tickets",
    foreignKeys = [ForeignKey(
        entity = EventDto::class,
        parentColumns = ["id"],
        childColumns = ["eventId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class TicketDto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val eventId: Long,
    val uri: String,
    val mimeType: String?,
    val displayName: String?
)

@Entity(
    tableName = "packingitems",
    foreignKeys = [ForeignKey(
        entity = TripDto::class,
        parentColumns = ["id"],
        childColumns = ["tripId"],
        onDelete = CASCADE
    )]
)
data class PackingListNodeDto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val isPacked: Boolean,
    val tripId: Long,
    val nodeType: Type,
    val ordinal: Int
) {
    enum class Type { ListItem, Header }
}

@Entity(
    tableName = "events",
    foreignKeys = [ForeignKey(
        entity = TripDto::class,
        parentColumns = ["id"],
        childColumns = ["tripId"],
        onDelete = CASCADE
    )]
)
data class EventDto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String?,
    val address: String?,
    val tripId: Long,
    val payload: Payload?
) {
    sealed class Payload(val type: Type) {
        data class TransportData(
            val departureDate: LocalDateTime?,
            val departureTime: LocalTime?,
            val arrivalDate: LocalDateTime?,
            val arrivalTime: LocalTime?
        ) : Payload(Type.Transport)

        data object NoData : Payload(Type.NoType)

        data class EntertainmentData(
            val startDate: LocalDateTime?,
            val startTime: LocalTime?,
            val endDate: LocalDateTime?,
            val endTime: LocalTime?
        ) : Payload(Type.Entertainment)

        data class AccommodationData(
            val checkInDate: LocalDateTime?,
            val checkInTimeFrom: LocalTime?,
            val checkInTimeTo: LocalTime?,
            val checkOutDate: LocalDateTime?,
            val checkOutTimeFrom: LocalTime?,
            val checkOutTimeTo: LocalTime?,
        ) : Payload(Type.Accommodation)
    }

    enum class Type {
        Accommodation, Transport, Entertainment, NoType
    }
}
