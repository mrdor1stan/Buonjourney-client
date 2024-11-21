package com.mrdor1stan.buonjourney.data

import com.mrdor1stan.buonjourney.data.db.BuonjourneyDao
import com.mrdor1stan.buonjourney.data.db.TripDto
import com.mrdor1stan.buonjourney.data.db.TripsDetailsDto
import kotlinx.coroutines.flow.Flow

class DatabaseRepository(private val dao: BuonjourneyDao) {
    suspend fun addTrip(trip: TripDto) = dao.addTrip(trip)
    fun getTrips(): Flow<List<TripsDetailsDto>> = dao.getTrips()
    fun getTrip(id: Int): Flow<TripsDetailsDto> = dao.getTrip(id)
    suspend fun deleteTrip(trip: TripDto) = dao.deleteTrip(trip)
    suspend fun deleteTrip(id: Int) = dao.deleteTrip(id)
}