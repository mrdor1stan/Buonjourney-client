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

    @Update
    suspend fun updateTrip(trip: TripDto)

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
    suspend fun addTicket(trip: TicketDto)

    @Transaction
    @Query("SELECT * FROM tickets")
    fun getTickets(): Flow<List<TicketDto>>

    @Transaction
    @Query("SELECT * FROM tickets WHERE eventId=:eventId")
    fun getTicketsByEvent(eventId: Long): Flow<List<TicketDto>>

    @Transaction
    @Query("SELECT * FROM tickets WHERE id=:id")
    fun getTicket(id: Long): Flow<TicketDto>

    @Delete
    suspend fun deleteTicket(ticket: TicketDto)

    @Query("DELETE FROM tickets WHERE id=:id")
    suspend fun deleteTicket(id: Long)

    @Update
    suspend fun updatePackingItem(item: PackingListNodeDto)

    @Query("UPDATE packingitems SET ordinal = :ordinal WHERE id=:itemId")
    suspend fun updatePackingItemOrdinal(itemId: Long, ordinal: Int)

    @Insert
    suspend fun addPackingItem(item: PackingListNodeDto)

    @Delete
    suspend fun deletePackingItem(item: PackingListNodeDto)

    @Query("SELECT * FROM events")
    fun getEvents(): Flow<List<EventDto>>

    @Query("SELECT * FROM events WHERE id=:eventId")
    fun getEvent(eventId: Long): Flow<EventDetailsDto>

    @Query("SELECT * FROM events WHERE tripId=:tripId")
    fun getEventsByTrip(tripId: Long): Flow<List<EventDto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addEvent(event: EventDto)

    @Delete
    suspend fun deleteEvent(trip: EventDto)

    @Query("DELETE FROM events WHERE id=:id")
    suspend fun deleteEvent(id: Long)
}
