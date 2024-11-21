package com.mrdor1stan.buonjourney.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface BuonjourneyDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTrip(trip: TripDto)

    @Transaction
    @Query("SELECT * FROM trips")
    fun getTrips(): Flow<List<TripsDetailsDto>>

    @Transaction
    @Query("SELECT * FROM trips WHERE id=:id")
    fun getTrip(id: Int): Flow<TripsDetailsDto>

    @Delete
    suspend fun deleteTrip(trip: TripDto)

    @Query("DELETE FROM trips WHERE id=:id")
    suspend fun deleteTrip(id: Int)
}