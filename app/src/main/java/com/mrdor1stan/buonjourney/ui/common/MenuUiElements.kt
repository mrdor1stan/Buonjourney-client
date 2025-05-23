package com.mrdor1stan.buonjourney.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.common.extentions.toShortString
import com.mrdor1stan.buonjourney.ui.entities.EventState
import com.mrdor1stan.buonjourney.ui.entities.TicketState
import com.mrdor1stan.buonjourney.ui.entities.TripState
import com.mrdor1stan.buonjourney.ui.screens.event.newone.icon
import com.mrdor1stan.buonjourney.ui.screens.event.newone.titleResId
import java.time.LocalDateTime

fun <T> getDefaultActions() = listOf<ActionState<T>>(
    ActionState(label = "delete_button", icon = Icons.Outlined.Delete, onClick = {}),
    ActionState(label = "edit_button", icon = Icons.Outlined.Edit, onClick = {}),
)


@Composable
fun TripElement(
    state: TripState,
    modifier: Modifier = Modifier,
    actions: List<ActionState<Long>>
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(
                id = R.dimen.middle_margin
            )
        ),

        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(imageVector = Icons.Default.FlightTakeoff, contentDescription = null)

        Row(
            Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(
                    id = R.dimen.small_margin
                )
            ), verticalAlignment = Alignment.CenterVertically
        ) {

            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Title(text = state.title)
                state.description?.let { Description(text = it) }
            }
            TripDateRange(state.startDate, state.endDate)
        }

        if (actions.isNotEmpty())
            ActionMenu(
                actions = actions,
                item = state,
                modifier = Modifier.clearAndSetSemantics { })
    }
}

@Composable
fun EventElement(
    state: EventState,
    modifier: Modifier = Modifier.padding(16.dp),
    actions: List<ActionState<Long>>
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_margin)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_margin))
        ) {
            state.payload?.eventType?.let {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_gap)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = it.icon, contentDescription = null)
                    Description(text = stringResource(id = it.titleResId))
                }
            }
            Title(text = state.title)
            state.description?.takeIf { it.isNotEmpty() }?.let {
                Description(text = state.description)
            }
            state.address?.takeIf { it.isNotEmpty() }?.let {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_gap)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Place, contentDescription = null)
                    Text(text = state.address)
                }
            }
        }
        if (actions.isNotEmpty())
            ActionMenu(
                actions = actions,
                item = state,
                modifier = Modifier.clearAndSetSemantics { })
    }
}


@Composable
fun TripDateRange(from: LocalDateTime?, to: LocalDateTime?, modifier: Modifier = Modifier) {
    Column(
        Modifier
            .width(IntrinsicSize.Max)
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        from?.let {
            BodyText(text = stringResource(id = R.string.from_date, from.toShortString))
        }
        if (from != null && to != null)
            HorizontalDivider()
        to?.let {
            BodyText(text = stringResource(id = R.string.to_date, to.toShortString))
        }
    }
}


@Composable
fun TicketElement(
    state: TicketState,
    modifier: Modifier = Modifier,
    actions: List<ActionState<Long>>
) {
    Row(
        modifier, horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(
                id = R.dimen.middle_margin
            )
        )
    ) {
        TicketThumbnail(
            ticket = state,
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        state.displayName?.let { Text(text = it, Modifier.weight(1f)) }
        ActionMenu(actions = actions, item = state, modifier = Modifier.clearAndSetSemantics { })
    }
}