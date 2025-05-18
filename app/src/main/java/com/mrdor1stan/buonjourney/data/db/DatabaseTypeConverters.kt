package com.mrdor1stan.buonjourney.data.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@TypeConverters
class DatabaseTypeConverters {

    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime): String =
        date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

    @TypeConverter
    fun toLocalDateTime(dateString: String): LocalDateTime =
        LocalDateTime.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

    @TypeConverter
    fun fromLocalTime(time: LocalTime): String =
        time.format(DateTimeFormatter.ISO_LOCAL_TIME)

    @TypeConverter
    fun toLocalTime(timeString: String): LocalTime =
        LocalTime.parse(timeString, DateTimeFormatter.ISO_LOCAL_TIME)

    private val gson: Gson = GsonBuilder()
        .registerTypeAdapter(LocalDateTime::class.java, object : JsonSerializer<LocalDateTime>,
            JsonDeserializer<LocalDateTime> {

            override fun serialize(
                src: LocalDateTime?,
                typeOfSrc: Type?,
                context: JsonSerializationContext?
            ): JsonElement = JsonPrimitive(src?.let { fromLocalDateTime(it) })

            override fun deserialize(
                json: JsonElement?,
                typeOfT: Type?,
                context: JsonDeserializationContext?
            ): LocalDateTime? = json?.asString?.let { toLocalDateTime(it) }
        })
        .registerTypeAdapter(LocalTime::class.java, object : JsonSerializer<LocalTime>,
            JsonDeserializer<LocalTime> {

            override fun serialize(
                src: LocalTime?,
                typeOfSrc: Type?,
                context: JsonSerializationContext?
            ): JsonElement = JsonPrimitive(src?.let { fromLocalTime(it) })

            override fun deserialize(
                json: JsonElement?,
                typeOfT: Type?,
                context: JsonDeserializationContext?
            ): LocalTime? = json?.asString?.let { toLocalTime(it) }
        })
        .create()


    @TypeConverter
    fun fromEventPayload(payload: EventDto.Payload): String {
        return gson.toJson(payload)
    }

    @TypeConverter
    fun toEventPayload(payloadJson: String): EventDto.Payload {
        var output: EventDto.Payload = EventDto.Payload.NoData
        val obj = JsonParser.parseString(payloadJson).asJsonObject
        when (obj["type"].asString) {
            EventDto.Type.NoType.name -> output = EventDto.Payload.NoData
            EventDto.Type.Transport.name -> output =
                gson.fromJson(payloadJson, EventDto.Payload.TransportData::class.java)

            EventDto.Type.Accommodation.name -> output =
                gson.fromJson(payloadJson, EventDto.Payload.AccommodationData::class.java)

            EventDto.Type.Entertainment.name -> output =
                gson.fromJson(payloadJson, EventDto.Payload.EntertainmentData::class.java)
        }
        return output
    }

}