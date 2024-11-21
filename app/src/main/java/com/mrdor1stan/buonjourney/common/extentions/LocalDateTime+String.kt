package com.mrdor1stan.buonjourney.common.extentions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val LocalDateTime.formatString
    get() = this.format(DateTimeFormatter.ofPattern("MMM dd, yyyy, HH:mm"))

val LocalDateTime.toShortString
    get() = this.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))