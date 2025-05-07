package com.mrdor1stan.buonjourney.data

import androidx.room.Transaction
import com.mrdor1stan.buonjourney.data.db.BuonjourneyDatabase
import com.mrdor1stan.buonjourney.data.db.EventDto
import com.mrdor1stan.buonjourney.data.db.PackingListNodeDto
import com.mrdor1stan.buonjourney.data.db.CityDto
import com.mrdor1stan.buonjourney.data.db.TicketDto
import com.mrdor1stan.buonjourney.data.db.TripDto
import com.mrdor1stan.buonjourney.data.db.TripsDetailsDto
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(
    private val database: BuonjourneyDatabase
) {
    private val dao get() = database.dao()

    suspend fun addTrip(trip: TripDto) = dao.addTrip(trip)

    fun getTrips(): Flow<List<TripsDetailsDto>> = dao.getTrips()

    fun getTrip(id: Long): Flow<TripsDetailsDto> = dao.getTrip(id)

    suspend fun deleteTrip(trip: TripDto) = dao.deleteTrip(trip)

    suspend fun deleteTrip(id: Long) = dao.deleteTrip(id)

    suspend fun addEvent(event: EventDto) = dao.addEvent(event)

    fun getEvents(): Flow<List<EventDto>> = dao.getEvents()

    fun getEventsByTrip(tripId: Long): Flow<List<EventDto>> = dao.getEventsByTrip(tripId)

    suspend fun deleteEvent(event: EventDto) = dao.deleteEvent(event)

    suspend fun deleteEvent(id: Long) = dao.deleteEvent(id)

    fun getTickets() = dao.getTickets()

    fun getTicket(id: Long) = dao.getTicket(id)

    suspend fun deleteTicket(ticket: TicketDto) = dao.deleteTicket(ticket)

    suspend fun deleteTicket(id: Long) = dao.deleteTicket(id)

    suspend fun addCity(place: CityDto) = dao.addCity(place)

    fun getCities(): Flow<List<CityDto>> = dao.getCities()

    fun getCity(id: String): Flow<CityDto> = dao.getCity(id)

    suspend fun deleteCity(place: CityDto) = dao.deleteCity(place)

    suspend fun deleteCity(id: String) = dao.deleteCity(id)

    suspend fun updateCity(city: CityDto) = dao.updateCity(city)

    suspend fun updatePackingItem(item: PackingListNodeDto) = dao.updatePackingItem(item)

    @Transaction
    suspend fun swapItemsOrder(item1: PackingListNodeDto, item2: PackingListNodeDto) {
        dao.updatePackingItemOrdinal(item1.id, item2.ordinal)
        dao.updatePackingItemOrdinal(item2.id, item1.ordinal)
    }

    suspend fun addPackingItem(item: PackingListNodeDto) = dao.addPackingItem(item)

    suspend fun deletePackingItem(item: PackingListNodeDto) = dao.deletePackingItem(item)

    suspend fun addTicket(trip: TicketDto) = dao.addTicket(trip)

    fun eraseDatabase() {
        database.clearAllTables()
    }
}
