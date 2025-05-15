package com.mrdor1stan.buonjourney.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.JsonParser
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Database(
    entities = [TripDto::class, TicketDto::class, PackingListNodeDto::class, EventDto::class],
    version = 6,
)
@TypeConverters(DbTypeConverters::class)
abstract class BuonjourneyDatabase : RoomDatabase() {
    abstract fun dao(): BuonjourneyDao

    companion object {
        @Volatile
        private var instance: BuonjourneyDatabase? = null
        private const val DATABASE_NAME = "buonjourney_db"

        fun getDatabase(context: Context): BuonjourneyDatabase =
            instance ?: synchronized(this) {
                Room
                    .databaseBuilder(context, BuonjourneyDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
    }
}

@TypeConverters
class DbTypeConverters {
    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime): String =
        date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

    @TypeConverter
    fun toLocalDateTime(dateString: String): LocalDateTime =
        LocalDateTime.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

    @TypeConverter
    fun fromEventPayload(payload: EventDto.Payload): String {
        return Gson().toJson(payload)
    }

    @TypeConverter
    fun toEventPayload(payloadJson: String): EventDto.Payload {
        var output: EventDto.Payload = EventDto.Payload.NoData
        val obj = JsonParser.parseString(payloadJson).asJsonObject
        when (obj["type"].asString) {
            EventDto.Type.NoType.name -> output = EventDto.Payload.NoData
            EventDto.Type.Transport.name -> output =
                Gson().fromJson(payloadJson, EventDto.Payload.TransportData::class.java)

            EventDto.Type.Accommodation.name -> output =
                Gson().fromJson(payloadJson, EventDto.Payload.AccommodationData::class.java)

            EventDto.Type.Entertainment.name -> output =
                Gson().fromJson(payloadJson, EventDto.Payload.EntertainmentData::class.java)
        }
        return output
    }

}
