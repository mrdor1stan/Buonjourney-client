package com.mrdor1stan.buonjourney.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.common.extentions.toShortString
import com.mrdor1stan.buonjourney.ui.common.BodyText
import com.mrdor1stan.buonjourney.ui.common.Description
import com.mrdor1stan.buonjourney.ui.common.Headline
import com.mrdor1stan.buonjourney.ui.common.TicketThumbnail
import com.mrdor1stan.buonjourney.ui.common.Title
import com.mrdor1stan.buonjourney.ui.entities.EventState
import com.mrdor1stan.buonjourney.ui.entities.TicketState
import com.mrdor1stan.buonjourney.ui.entities.TripState
import com.mrdor1stan.buonjourney.ui.screens.event.newone.icon
import com.mrdor1stan.buonjourney.ui.screens.event.newone.titleResId


@Composable
fun TripCard(
    state: TripState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_margin))
    ) {
        Icon(imageVector = Icons.Default.FlightTakeoff, contentDescription = null)
        Spacer(modifier = Modifier.weight(1f))
        Headline(text = state.title)
        state.description?.let { Description(text = it) }

        if (state.endDate != null || state.startDate != null)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.small_margin)
                ),
                modifier = Modifier.semantics(mergeDescendants = true) {}
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "Date",
                    modifier = Modifier.size(24.dp)
                )
                Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_margin))) {
                    val date =
                        "${state.startDate?.toShortString ?: "..."} - ${state.endDate?.toShortString ?: "..."}"
                    BodyText(text = date)
                }
            }

    }
}

@Composable
fun EventCard(
    state: EventState,
    modifier: Modifier = Modifier.padding(16.dp)
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.middle_margin)),
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {

        state.payload?.eventType?.let {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_gap)),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.semantics(mergeDescendants = true) { }
            ) {
                Icon(imageVector = it.icon, contentDescription = null)
                Description(text = stringResource(id = it.titleResId))
            }
        }
        Title(text = state.title)
        state.description?.takeIf { it.isNotEmpty() }?.let {
            Description(text = state.description)
        }
        Spacer(modifier = Modifier.weight(1f))
        state.address?.takeIf { it.isNotEmpty() }?.let {
            Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_gap))) {
                Icon(Icons.Default.Place, contentDescription = null)
                Text(text = state.address)
            }
        }
    }

}


@Composable
fun TicketCard(
    state: TicketState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
    ) {
        TicketThumbnail(
            state,
            Modifier
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .weight(5f)
                .fillMaxWidth()
        )
        val displayName = StringBuilder()
        state.eventName?.let { displayName.append(it) }
        if (state.eventName != null && state.displayName != null) displayName.append(" / ")
        state.displayName?.let { displayName.append(it) }
        displayName.toString().takeIf { it.isNotEmpty() }?.let {
            Text(
                text = it,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.small_margin))
                    .weight(3f)
                    .fillMaxWidth()
            )
        }
    }
}