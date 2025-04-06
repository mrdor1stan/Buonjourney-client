package com.mrdor1stan.buonjourney.ui.entities
import com.mrdor1stan.buonjourney.data.db.EventDto
import com.mrdor1stan.buonjourney.data.db.PackingListItemDto
import com.mrdor1stan.buonjourney.data.db.PackingListDto
import com.mrdor1stan.buonjourney.data.db.TicketDto
import com.mrdor1stan.buonjourney.data.db.TripDto
import java.time.LocalDateTime

interface DataState {
    val id: Long?
}

data class TripState(
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val title: String,
    val destination: String,
    val status: TripDto.TripStatus,
    val packingLists: List<PackingListDto>,
    val events: List<EventDto>,
    val tickets: List<TicketDto>,
    override val id: Long? = null,
): DataState

data class TicketState(
    val fileUrl: String,
    val dateTime: LocalDateTime,
    val caption: String,
    val isUsed: Boolean,
    val ticketType: TicketDto.TicketType
)

data class PlaceState(
    val name: String,
    override val id: Long? = null
): DataState

data class PackingListState(
    val name: String,
    val items: List<PackingListItemDto>,
    override val id: Long? = null
): DataState

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