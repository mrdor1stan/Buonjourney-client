package com.mrdor1stan.buonjourney.ui.screens.packinglist.details

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material.icons.filled.Reorder
import com.mrdor1stan.buonjourney.R

enum class PackingListScreenMode { Check, Edit,  Reorder }

fun PackingListScreenMode.titleResId() =
    when (this) {
        PackingListScreenMode.Check -> R.string.check_mode
        PackingListScreenMode.Reorder -> R.string.reorder_mode
        PackingListScreenMode.Edit -> R.string.edit_mode
    }

fun PackingListScreenMode.icon() =
    when (this) {
        PackingListScreenMode.Check -> Icons.Default.Checklist
        PackingListScreenMode.Reorder -> Icons.Default.Reorder
        PackingListScreenMode.Edit -> Icons.Default.ModeEdit
    }