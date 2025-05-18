package com.mrdor1stan.buonjourney.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class TripsDetailsDto(
    @Embedded val trip: TripDto,
    @Relation(
        parentColumn = "id",
        entityColumn = "tripId",
    )
    val events: List<EventDto>,
    @Relation(
        parentColumn = "id",
        entityColumn = "tripId",
    )
    val packingItems: List<PackingListNodeDto>
)

data class EventDetailsDto(
    @Embedded val event: EventDto,
    @Relation(
        parentColumn = "id",
        entityColumn = "eventId",
    )
    val tickets: List<TicketDto>,
)

