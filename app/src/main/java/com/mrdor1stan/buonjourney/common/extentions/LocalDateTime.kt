package com.mrdor1stan.buonjourney.common.extentions

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val LocalDateTime.formatString
    get() = this.format(DateTimeFormatter.ofPattern("MMM dd, yyyy, HH:mm"))

val LocalDateTime.toShortString
    get() = this.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

val LocalDateTime.millis
    get() = this.toInstant(ZoneOffset.UTC).toEpochMilli()

val Long.dateFromMillis: LocalDateTime
    get() = LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.of("UTC"))