package com.mrdor1stan.buonjourney.data

import com.mrdor1stan.buonjourney.data.db.BuonjourneyDao
import com.mrdor1stan.buonjourney.data.db.EventDto
import com.mrdor1stan.buonjourney.data.db.PackingItemDto
import com.mrdor1stan.buonjourney.data.db.PackingListDto
import com.mrdor1stan.buonjourney.data.db.PlaceDto
import com.mrdor1stan.buonjourney.data.db.TripDto
import com.mrdor1stan.buonjourney.data.db.TripsDetailsDto
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(private val dao: BuonjourneyDao) {
    suspend fun addTrip(trip: TripDto) = dao.addTrip(trip)
    fun getTrips(): Flow<List<TripsDetailsDto>> = dao.getTrips()
    fun getTrip(id: Long): Flow<TripsDetailsDto> = dao.getTrip(id)
    suspend fun deleteTrip(trip: TripDto) = dao.deleteTrip(trip)
    suspend fun deleteTrip(id: Int) = dao.deleteTrip(id)
    suspend fun addPlace(place: PlaceDto) = dao.addPlace(place)
    fun getPlaces(): Flow<List<PlaceDto>> = dao.getPlaces()
    fun getPlace(id: Int): Flow<PlaceDto> = dao.getPlace(id)
    suspend fun deletePlaces(place: PlaceDto) = dao.deletePlaces(place)
    suspend fun deletePlaces(id: Int) = dao.deletePlaces(id)
    fun getPackingListsByTrip(tripId: Long) = dao.getPackingListsByTrip(tripId)
    suspend fun addPackingList(list: PackingListDto) = dao.addPackingList(list)
    fun getPackingList(listId: Long) = dao.getPackingList(listId)
    suspend fun updatePackingItem(item: PackingItemDto) = dao.updatePackingItem(item)
    suspend fun addPackingItem(item: PackingItemDto) = dao.addPackingItem(item)
    fun getEvents(): Flow<List<EventDto>> = dao.getEvents()
    fun getEventsByTrip(tripId: Long): Flow<List<EventDto>> = dao.getEventsByTrip(tripId)
    suspend fun addEvent(event: EventDto) = dao.addEvent(event)
}