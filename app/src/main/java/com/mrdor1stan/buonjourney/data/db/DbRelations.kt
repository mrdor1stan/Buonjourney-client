package com.mrdor1stan.buonjourney.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class TripsDetailsDto(
    @Embedded val trip: TripDto,
    @Relation(
        parentColumn = "id",
        entityColumn = "tripId",
    )
    val tickets: List<TicketDto>,
    @Relation(
        parentColumn = "id",
        entityColumn = "tripId",
    )
    val events: List<EventDto>,
    @Relation(
        parentColumn = "id",
        entityColumn = "tripId",
    )
    val packingLists: List<PackingListDto>,
    @Relation(
        parentColumn = "placeId",
        entityColumn = "id",
    )
    val place: PlaceDto,
)

data class PackingListWithItemsDto(
    @Embedded val list: PackingListDto,
    @Relation(
        parentColumn = "id",
        entityColumn = "listId",
    )
    val items: List<PackingListItemDto>,
)
