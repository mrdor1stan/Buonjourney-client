package com.mrdor1stan.buonjourney.data

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import com.mrdor1stan.buonjourney.data.db.BuonjourneyDao
import com.mrdor1stan.buonjourney.data.db.PlaceDto
import com.mrdor1stan.buonjourney.data.db.TripDto
import com.mrdor1stan.buonjourney.data.db.TripsDetailsDto
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(private val dao: BuonjourneyDao) {
    suspend fun addTrip(trip: TripDto) = dao.addTrip(trip)
    fun getTrips(): Flow<List<TripsDetailsDto>> = dao.getTrips()
    fun getTrip(id: Int): Flow<TripsDetailsDto> = dao.getTrip(id)
    suspend fun deleteTrip(trip: TripDto) = dao.deleteTrip(trip)
    suspend fun deleteTrip(id: Int) = dao.deleteTrip(id)
    suspend fun addPlace(place: PlaceDto) = dao.addPlace(place)
    fun getPlaces(): Flow<List<PlaceDto>> = dao.getPlaces()
    fun getPlace(id: Int): Flow<PlaceDto> = dao.getPlace(id)
    suspend fun deletePlaces(place: PlaceDto) = dao.deletePlaces(place)
    suspend fun deletePlaces(id: Int) = dao.deletePlaces(id)
}