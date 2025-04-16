package com.mrdor1stan.buonjourney.data.db

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TripsDetailsDto(
    @Embedded val trip: TripDto,
    @Relation(
        parentColumn = "id",
        entityColumn = "tripId",
    )
    val events: List<EventDto>,
    @Relation(
        entity = PackingListDto::class,
        parentColumn = "id",
        entityColumn = "tripId",
    )
    val packingList: PackingListWithItemsDto,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            TripCityCrossRef::class,
            parentColumn = "tripId",
            entityColumn = "cityId"
        )
    )
    val cities: List<CityDto>
)

data class PackingListWithItemsDto(
    @Embedded val list: PackingListDto,
    @Relation(
        parentColumn = "id",
        entityColumn = "listId",
    )
    val items: List<PackingListItemDto>,
)
