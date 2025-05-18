package com.mrdor1stan.buonjourney.ui.screens.event.details

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.common.extentions.queryDisplayName
import com.mrdor1stan.buonjourney.ui.common.ItemsListWithHeader
import com.mrdor1stan.buonjourney.ui.common.Loader
import com.mrdor1stan.buonjourney.ui.common.TicketElement

@Composable
fun EventDetailsScreen(
    eventId: Long,
    viewModel: EventDetailsScreenViewModel = viewModel(
        factory = EventDetailsScreenViewModel.Factory(
            eventId
        )
    ),
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            val contentResolver = context.contentResolver
            // даємо персистентний доступ (щоб мати URI і після перезавантаження)
            contentResolver.takePersistableUriPermission(
                it, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            val mime = contentResolver.getType(it)
            val name = contentResolver.queryDisplayName(it)
            viewModel.addTicket(it, mime, name)
        }
    }

    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        uri?.let {
            val contentResolver = context.contentResolver
            contentResolver.takePersistableUriPermission(
                it, Intent.FLAG_GRANT_READ_URI_PERMISSION
            )

            val mime = contentResolver.getType(it)
            val name = contentResolver.queryDisplayName(it)
            viewModel.addTicket(it, mime, name)
        }
    }
    when (val event = state.event) {
        null -> Loader()
        else -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(modifier),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_margin))
            ) {

                Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.middle_margin))) {

                    Button(
                        onClick = {
                            imagePickerLauncher.launch(arrayOf("image/*"))
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.AddPhotoAlternate, null)
                        Spacer(Modifier.width(4.dp))
                        Text("Add image")
                    }

                    Button(
                        onClick = {
                            pdfPickerLauncher.launch(arrayOf("application/pdf"))
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Default.PictureAsPdf, null)
                        Spacer(Modifier.width(4.dp))
                        Text("Add PDF")
                    }
                }

                ItemsListWithHeader(
                    header = "Tickets",
                    items = state.tickets,
                    navigateToAddScreen = null
                ) { ticket, actions ->
                    TicketElement(ticket, modifier = Modifier
                        .clickable {
                            ticket.mimeType?.let { mimeType ->
                                openTicket(
                                    context,
                                    ticket.uri,
                                    mimeType
                                )
                            }
                        }, actions
                    )
                }

            }
        }
    }
}

private fun openTicket(context: Context, uri: Uri, mimeType: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, mimeType)
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }

    try {
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No app found to open this file", Toast.LENGTH_SHORT).show()
    }
}