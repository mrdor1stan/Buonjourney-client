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
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val title: String,
    val status: TripStatus,
) {
    enum class TripStatus {
        PLANNED,
        ONGOING,
        COMPLETED,
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
    val tripId: Long,
)

@Entity(tableName = "cities")
data class CityDto(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val country: String
)


@Entity(
    primaryKeys = ["cityId", "tripId"],
    foreignKeys = [ForeignKey(
        entity = TripDto::class,
        parentColumns = ["id"],
        childColumns = ["tripId"],
    ),
        ForeignKey(
            entity = CityDto::class,
            parentColumns = ["id"],
            childColumns = ["cityId"],
        )]
)
data class TripCityCrossRef(
    val cityId: String,
    val tripId: Long
)


@Entity(
    tableName = "attractions",
    foreignKeys = [ForeignKey(
        entity = CityDto::class,
        parentColumns = ["id"],
        childColumns = ["cityId"],
        onDelete = CASCADE
    )]
)
data class AttractionDto(
    @PrimaryKey val id: String,
    val name: String,
    val cityId: String,
    val description: String?,
    val latitude: Double?,
    val longitude: Double?,
    val source: SourceType
) {
    enum class SourceType {
        MANUAL,
        GOOGLE_LINK,
        GOOGLE_API
    }
}

@Entity(
    tableName = "packinglists",
    foreignKeys = [ForeignKey(
        entity = TripDto::class,
        parentColumns = ["id"],
        childColumns = ["tripId"],
        onDelete = CASCADE
    )]
)
data class PackingListDto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val tripId: Long,
)

@Entity(
    tableName = "packingitems",
    foreignKeys = [ForeignKey(
        entity = PackingListDto::class,
        parentColumns = ["id"],
        childColumns = ["listId"],
        onDelete = CASCADE
    )]
)
data class PackingListItemDto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val isPacked: Boolean,
    val listId: Long,
)


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
