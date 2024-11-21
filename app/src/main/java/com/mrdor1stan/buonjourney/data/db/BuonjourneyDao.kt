package com.mrdor1stan.buonjourney.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
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
    fun getTrip(id: Long): Flow<TripsDetailsDto>

    @Delete
    suspend fun deleteTrip(trip: TripDto)

    @Query("DELETE FROM trips WHERE id=:id")
    suspend fun deleteTrip(id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlace(place: PlaceDto)

    @Transaction
    @Query("SELECT * FROM places")
    fun getPlaces(): Flow<List<PlaceDto>>

    @Transaction
    @Query("SELECT * FROM places WHERE id=:id")
    fun getPlace(id: Int): Flow<PlaceDto>

    @Delete
    suspend fun deletePlaces(place: PlaceDto)

    @Query("DELETE FROM places WHERE id=:id")
    suspend fun deletePlaces(id: Int)

    @Query("SELECT * FROM packinglists WHERE tripId=:tripId")
    fun getPackingListsByTrip(tripId: Long): Flow<List<PackingListWithItemsDto>>

    @Insert
    suspend fun addPackingList(list: PackingListDto)

    @Query("SELECT * FROM packinglists WHERE id=:listId")
    fun getPackingList(listId: Long): Flow<PackingListWithItemsDto>

    @Update
    suspend fun updatePackingItem(item: PackingItemDto)

    @Insert
    suspend fun addPackingItem(item: PackingItemDto)
}