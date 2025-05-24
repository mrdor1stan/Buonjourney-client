package com.mrdor1stan.buonjourney.common.extentions

import androidx.compose.runtime.Composable
import androidx.room.Database
import androidx.room.TypeConverter
import com.mrdor1stan.buonjourney.data.db.DatabaseTypeConverters
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val LocalDateTime.formatString
    get() = this.format(DateTimeFormatter.ofPattern("MMM dd, yyyy, HH:mm"))

val LocalDateTime.toShortString
    get() = this.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

val LocalTime.toString
    get() = this.format(DateTimeFormatter.ofPattern("HH:mm"))

val LocalDateTime.millis
    get() = this.toInstant(ZoneOffset.UTC).toEpochMilli()

val Long.dateFromMillis: LocalDateTime
    get() = LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.of("UTC"))


object LocalDateTimeSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("java.time.LocalDateTime", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: LocalDateTime) = encoder.encodeString(encodeLocalDateTime(value))
    override fun deserialize(decoder: Decoder): LocalDateTime = parseLocalDateTime(decoder.decodeString())
}

object LocalTimeSerializer : KSerializer<LocalTime> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("java.time.LocalTime", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: LocalTime) = encoder.encodeString(encodeLocalTime(value))
    override fun deserialize(decoder: Decoder): LocalTime = parseLocalTime(decoder.decodeString())
}


fun encodeLocalDateTime(date: LocalDateTime): String =
    date.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

fun parseLocalDateTime(dateString: String): LocalDateTime =
    LocalDateTime.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

fun encodeLocalTime(time: LocalTime): String =
    time.format(DateTimeFormatter.ISO_LOCAL_TIME)

fun parseLocalTime(timeString: String): LocalTime =
    LocalTime.parse(timeString, DateTimeFormatter.ISO_LOCAL_TIME)
