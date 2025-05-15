package com.mrdor1stan.buonjourney.ui.entities

import com.mrdor1stan.buonjourney.data.db.EventDto
import com.mrdor1stan.buonjourney.data.db.PackingListNodeDto
import com.mrdor1stan.buonjourney.data.db.TripsDetailsDto
import java.time.LocalDateTime

interface DataState<T> {
    val id: T?
}

data class TripState(
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
    val title: String,
    val description: String?,
    val packingList: List<PackingListNodeDto>,
    val events: List<EventState>,
    override val id: Long? = null,
) : DataState<Long>


fun TripsDetailsDto.map() =
    TripState(
        startDate = trip.startDate,
        endDate = trip.endDate,
        title = trip.title,
        description = trip.description,
        packingList = packingItems,
        events = events.map(EventDto::map),
        id = trip.id
    )

data class EventState(
    val title: String,
    val description: String,
    val address: String,
)

fun EventDto.map() = EventState(title = title, description = description, address = address)