package com.mrdor1stan.buonjourney.ui.entities

import android.net.Uri
import android.provider.ContactsContract.Data
import androidx.core.net.toUri
import com.mrdor1stan.buonjourney.data.db.EventDto
import com.mrdor1stan.buonjourney.data.db.PackingListNodeDto
import com.mrdor1stan.buonjourney.data.db.TicketDto
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
    val description: String?,
    val address: String?,
    val payload: EventDto.Payload?,
    override val id: Long
): DataState<Long>

fun EventDto.map() = EventState(
    title = title,
    description = description,
    address = address,
    payload = payload,
    id = id
)

data class TicketState(
    override val id: Long,
    val uri: Uri,
    val mimeType: String?,
    val displayName: String?
): DataState<Long>

fun TicketDto.map() = TicketState(
    id = id,
    uri = uri.toUri(),
    mimeType = mimeType,
    displayName = displayName
)