package com.mrdor1stan.buonjourney.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.common.extentions.toShortString
import com.mrdor1stan.buonjourney.ui.entities.EventState
import com.mrdor1stan.buonjourney.ui.entities.TicketState
import com.mrdor1stan.buonjourney.ui.entities.TripState
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
        modifier, horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(
                id = R.dimen.middle_margin
            )
        )
    ) {
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

        ActionMenu(actions = actions, item = state, modifier = Modifier.clearAndSetSemantics { })
    }
}

@Composable
fun EventElement(
    state: EventState,
    modifier: Modifier = Modifier.padding(16.dp),
    actions: List<ActionState<Long>>
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_gap))) {
            Headline(text = state.title, modifier = Modifier.weight(1f))
            ActionMenu(
                actions = actions,
                item = state,
                modifier = Modifier.clearAndSetSemantics { })
        }
        state.description?.takeIf { it.isNotEmpty() }?.let {
            Description(text = state.description)
        }
        state.address?.takeIf { it.isNotEmpty() }?.let {
            Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_gap))) {
                Icon(Icons.Default.Place, contentDescription = null)
                Text(text = state.address)
            }
        }
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
        TicketThumbnail(state)
        state.displayName?.let { Text(text = it, Modifier.weight(1f)) }
        ActionMenu(actions = actions, item = state, modifier = Modifier.clearAndSetSemantics { })
    }
}