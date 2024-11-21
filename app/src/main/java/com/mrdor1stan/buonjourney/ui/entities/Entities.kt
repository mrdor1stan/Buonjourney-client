package com.mrdor1stan.buonjourney.ui.entities
import com.mrdor1stan.buonjourney.data.db.EventDto
import com.mrdor1stan.buonjourney.data.db.PackingItemDto
import com.mrdor1stan.buonjourney.data.db.PackingListDto
import com.mrdor1stan.buonjourney.data.db.TicketDto
import com.mrdor1stan.buonjourney.data.db.TripDto
import java.time.LocalDateTime

data class TripState(
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val title: String,
    val destination: String,
    val status: TripDto.TripStatus,
    val packingLists: List<PackingListDto>,
    val events: List<EventDto>,
    val tickets: List<TicketDto>,
)

data class TicketState(
    val fileUrl: String,
    val dateTime: LocalDateTime,
    val caption: String,
    val isUsed: Boolean,
    val ticketType: TicketDto.TicketType
)

data class PlaceState(
    val name: String
)

data class PackingListState(
    val name: String,
    val items: List<PackingItemDto>
)

data class PackingItemState(
    val name: String,
    val isPacked: Boolean
)

data class EventState(
    val title: String,
    val dateTime: LocalDateTime,
    val description: String,
    val address: String,
)