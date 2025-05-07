package com.mrdor1stan.buonjourney.ui.entities

import android.provider.ContactsContract.Data
import androidx.room.PrimaryKey
import com.mrdor1stan.buonjourney.data.db.EventDto
import com.mrdor1stan.buonjourney.data.db.PackingListNodeDto
import com.mrdor1stan.buonjourney.data.db.CityDto
import com.mrdor1stan.buonjourney.data.db.PackingListNodeDto.Type
import com.mrdor1stan.buonjourney.data.db.TripDto
import com.mrdor1stan.buonjourney.data.db.TripsDetailsDto
import java.time.LocalDateTime

interface DataState<T> {
    val id: T?
}

data class TripState(
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val title: String,
    val destinations: List<CityState>,
    val status: TripDto.TripStatus,
    val packingList: List<PackingListNodeDto>,
    val events: List<EventState>,
    override val id: Long? = null,
) : DataState<Long>


fun TripsDetailsDto.map() =
    TripState(
        startDate = trip.startDate,
        endDate = trip.endDate,
        title = trip.title,
        destinations = cities.map(CityDto::map),
        status = trip.status,
        packingList = packingItems,
        events = events.map(EventDto::map),
        id = trip.id
    )

data class CityState(
    val name: String,
    val country: String,
    override val id: String? = null,
) : DataState<String>

fun CityDto.map() = CityState(name = name, id = id, country = country)

data class EventState(
    val title: String,
    val dateTime: LocalDateTime,
    val description: String,
    val address: String,
)

fun EventDto.map() = EventState(title = title,  dateTime = dateTime, description = description, address = address)