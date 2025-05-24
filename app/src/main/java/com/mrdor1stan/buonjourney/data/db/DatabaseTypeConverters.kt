package com.mrdor1stan.buonjourney.data.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.mrdor1stan.buonjourney.common.extentions.encodeLocalDateTime
import com.mrdor1stan.buonjourney.common.extentions.encodeLocalTime
import com.mrdor1stan.buonjourney.common.extentions.parseLocalDateTime
import com.mrdor1stan.buonjourney.common.extentions.parseLocalTime
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.time.LocalTime

@TypeConverters
class DatabaseTypeConverters {

    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime): String =
        encodeLocalDateTime(date)

    @TypeConverter
    fun toLocalDateTime(dateString: String): LocalDateTime =
        parseLocalDateTime(dateString)

    @TypeConverter
    fun fromLocalTime(time: LocalTime): String =
        encodeLocalTime(time)

    @TypeConverter
    fun toLocalTime(timeString: String): LocalTime =
        parseLocalTime(timeString)


    @TypeConverter
    fun fromEventPayload(payload: EventDto.Payload): String {
        return Json.encodeToString(EventDto.Payload.serializer(), payload)
    }

    @TypeConverter
    fun toEventPayload(payloadJson: String): EventDto.Payload {
        return Json.decodeFromString<EventDto.Payload>(payloadJson)
    }
}