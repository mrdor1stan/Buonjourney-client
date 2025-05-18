package com.mrdor1stan.buonjourney.common.extentions

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns

fun ContentResolver.queryDisplayName(uri: Uri): String? =
    query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
        ?.use { cursor ->
            if (cursor.moveToFirst()) {
                cursor.getString(0)
            } else null
        }