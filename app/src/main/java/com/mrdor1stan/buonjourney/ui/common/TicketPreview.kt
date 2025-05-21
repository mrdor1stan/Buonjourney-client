package com.mrdor1stan.buonjourney.ui.common

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Build
import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
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
fun TicketThumbnail(ticket: TicketState, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    BoxWithConstraints(modifier) {
        when {
            ticket.mimeType?.startsWith("image") == true -> {
                Image(
                    painter = rememberAsyncImagePainter(ticket.uri),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                return@BoxWithConstraints
            }

            ticket.mimeType == "application/pdf" -> {
                val bitmap by produceState<Bitmap?>(initialValue = null, ticket.uri) {
                    value = loadPdfThumbnail(context, ticket.uri, constraints.maxWidth, constraints.maxHeight)
                }

                if (bitmap != null) {
                    Image(
                        bitmap = bitmap!!.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        Icons.Default.PictureAsPdf,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                return@BoxWithConstraints
            }

            else -> Icon(
                Icons.AutoMirrored.Default.InsertDriveFile,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

suspend fun loadPdfThumbnail(
    context: Context,
    uri: Uri,
    width: Int,
    height: Int
): Bitmap? = withContext(Dispatchers.IO) {
    runCatching {
        if (Build.VERSION.SDK_INT >= 29) {
            context.contentResolver.loadThumbnail(
                uri,
                Size(width, height),
                null
            )
        } else null
    }.getOrNull()
        ?: createPdfRendererThumbnail(context, uri, width, height)
}

private fun createPdfRendererThumbnail(
    context: Context,
    uri: Uri,
    width: Int,
    height: Int
): Bitmap? {
    context.contentResolver.openFileDescriptor(uri, "r")?.use { fd ->
        PdfRenderer(fd).use { renderer ->
            if (renderer.pageCount > 0) {
                renderer.openPage(0).use { page ->
                    val bitmap =
                        Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
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
