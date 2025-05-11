package com.mrdor1stan.buonjourney.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

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

@Entity(tableName = "tickets")
data class TicketDto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val fileUri: String,
    val dateTime: LocalDateTime,
    val caption: String,
    val isUsed: Boolean,
    val tripId: Long,
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
    val dateTime: LocalDateTime,
    val description: String,
    val address: String,
    val tripId: Long,
    val eventType: EventType = EventType.NO_TYPE
) {
    enum class EventType {
        TRANSPORT,
        ENTERTAINMENT,
        ACCOMMODATION,
        NO_TYPE,
    }
}
