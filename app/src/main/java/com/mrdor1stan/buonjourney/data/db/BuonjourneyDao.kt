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
    suspend fun deleteTrip(id: Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlace(place: PlaceDto)

    @Transaction
    @Query("SELECT * FROM places")
    fun getPlaces(): Flow<List<PlaceDto>>

    @Transaction
    @Query("SELECT * FROM places WHERE id=:id")
    fun getPlace(id: Long): Flow<PlaceDto>

    @Delete
    suspend fun deletePlace(place: PlaceDto)

    @Query("DELETE FROM places WHERE id=:id")
    suspend fun deletePlace(id: Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTicket(trip: TicketDto)

    @Transaction
    @Query("SELECT * FROM tickets")
    fun getTickets(): Flow<List<TicketDto>>

    @Transaction
    @Query("SELECT * FROM tickets WHERE id=:id")
    fun getTicket(id: Long): Flow<TicketDto>

    @Delete
    suspend fun deleteTicket(ticket: TicketDto)

    @Query("DELETE FROM tickets WHERE id=:id")
    suspend fun deleteTicket(id: Long)

    @Query("SELECT * FROM packinglists WHERE tripId=:tripId")
    fun getPackingListsByTrip(tripId: Long): Flow<List<PackingListWithItemsDto>>

    @Insert
    suspend fun addPackingList(list: PackingListDto)

    @Query("SELECT * FROM packinglists WHERE id=:id")
    fun getPackingList(id: Long): Flow<PackingListWithItemsDto>

    @Delete
    suspend fun deletePackingList(list: PackingListDto)

    @Query("DELETE FROM packinglists WHERE id=:id")
    suspend fun deletePackingList(id: Long)

    @Update
    suspend fun updatePackingItem(item: PackingListItemDto)

    @Insert
    suspend fun addPackingItem(item: PackingListItemDto)

    @Delete
    suspend fun deletePackingItem(item: PackingListItemDto)

    @Query("SELECT * FROM events ORDER BY dateTime DESC")
    fun getEvents(): Flow<List<EventDto>>

    @Query("SELECT * FROM events WHERE tripId=:tripId ORDER BY dateTime DESC")
    fun getEventsByTrip(tripId: Long): Flow<List<EventDto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addEvent(event: EventDto)

    @Delete
    suspend fun deleteEvent(trip: EventDto)

    @Query("DELETE FROM events WHERE id=:id")
    suspend fun deleteEvent(id: Long)
}
