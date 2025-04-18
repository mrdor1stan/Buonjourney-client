package com.mrdor1stan.buonjourney.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Database(
    entities = [TripDto::class, TicketDto::class, CityDto::class, PackingListDto::class, PackingListItemDto::class, EventDto::class, TripCityCrossRef::class],
    version = 2,
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
    fun fromLocalDateTime(date: LocalDateTime): String = date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

    @TypeConverter
    fun toLocalDateTime(dateString: String): LocalDateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}
