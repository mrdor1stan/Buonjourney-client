package com.mrdor1stan.buonjourney.ui.screens.event.newone

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Museum
import androidx.compose.material.icons.filled.NotInterested
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.data.db.EventDto

val EventDto.Type.icon
    get() = when (this) {
        EventDto.Type.Accommodation -> Icons.Default.Hotel
        EventDto.Type.Transport -> Icons.Default.DirectionsBus
        EventDto.Type.Entertainment -> Icons.Default.Museum
        EventDto.Type.NoType -> Icons.Default.NotInterested
    }

val EventDto.Type.titleResId
    get() = when (this) {
        EventDto.Type.Accommodation -> R.string.accommodation
        EventDto.Type.Transport -> R.string.transport
        EventDto.Type.Entertainment -> R.string.entertaimment
        EventDto.Type.NoType -> R.string.no_type
    }

