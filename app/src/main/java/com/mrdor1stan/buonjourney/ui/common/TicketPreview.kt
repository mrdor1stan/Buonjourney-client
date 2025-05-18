package com.mrdor1stan.buonjourney.ui.common

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Build
import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.InsertDriveFile
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.mrdor1stan.buonjourney.ui.entities.TicketState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt

@Composable
fun TicketThumbnail(ticket: TicketState, size: Dp = 72.dp) {
    val context = LocalContext.current

    when {
        ticket.mimeType?.startsWith("image") == true -> {
            Image(
                painter = rememberAsyncImagePainter(ticket.uri),
                contentDescription = null,
                modifier = Modifier
                    .size(size)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            return
        }

        ticket.mimeType == "application/pdf" -> {
            val bitmap by produceState<Bitmap?>(initialValue = null, ticket.uri) {
                value = loadPdfThumbnail(context, ticket.uri, size)
            }

            if (bitmap != null) {
                Image(
                    bitmap = bitmap!!.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(size)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    Icons.Default.PictureAsPdf,
                    contentDescription = null,
                    modifier = Modifier.size(size)
                )
            }
            return
        }

        else -> Icon(
            Icons.AutoMirrored.Default.InsertDriveFile,
            contentDescription = null,
            modifier = Modifier.size(size)
        )
    }
}

suspend fun loadPdfThumbnail(
    context: Context,
    uri: Uri,
    size: Dp
): Bitmap? = withContext(Dispatchers.IO) {
    runCatching {
        if (Build.VERSION.SDK_INT >= 29) {
            context.contentResolver.loadThumbnail(
                uri,
                Size(size.roundToPx(), size.roundToPx()),
                null
            )
        } else null
    }.getOrNull()
        ?: createPdfRendererThumbnail(context, uri, size)
}

private fun createPdfRendererThumbnail(
    context: Context,
    uri: Uri,
    size: Dp
): Bitmap? {
    val px = size.roundToPx()
    context.contentResolver.openFileDescriptor(uri, "r")?.use { fd ->
        PdfRenderer(fd).use { renderer ->
            if (renderer.pageCount > 0) {
                renderer.openPage(0).use { page ->
                    val bitmap =
                        Bitmap.createBitmap(px, px, Bitmap.Config.ARGB_8888)
                    page.render(
                        bitmap, null, null,
                        PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
                    )
                    return bitmap
                }
            }
        }
    }
    return null
}

fun Dp.roundToPx() = (this.value * Resources.getSystem().displayMetrics.density).roundToInt()