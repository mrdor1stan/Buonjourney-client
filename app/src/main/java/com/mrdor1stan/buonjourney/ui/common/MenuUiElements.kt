package com.mrdor1stan.buonjourney.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.common.extentions.formatString
import com.mrdor1stan.buonjourney.common.extentions.toShortString
import com.mrdor1stan.buonjourney.data.db.TicketDto
import com.mrdor1stan.buonjourney.data.db.TripDto
import com.mrdor1stan.buonjourney.ui.entities.EventState
import com.mrdor1stan.buonjourney.ui.entities.PackingItemState
import com.mrdor1stan.buonjourney.ui.entities.PackingListState
import com.mrdor1stan.buonjourney.ui.entities.PlaceState
import com.mrdor1stan.buonjourney.ui.entities.TicketState
import com.mrdor1stan.buonjourney.ui.entities.TripState
import java.time.LocalDateTime

val DEFAULT_ACTIONS = listOf(
    ActionState(label = "delete_button", icon = Icons.Outlined.Delete, onClick = {}),
    ActionState(label = "edit_button", icon = Icons.Outlined.Edit, onClick = {}),
)

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun TripElement(
    state: TripState =
        TripState(
            LocalDateTime.now(),
            LocalDateTime.now().plusDays(7),
            "Mega trip",
            "Kyiv, Ukraine",
            TripDto.TripStatus.PLANNED,
            packingLists = listOf(),
            events = listOf(),
            tickets = listOf()
        ), modifier: Modifier = Modifier.padding(16.dp),
    actions: List<ActionState> = DEFAULT_ACTIONS
) {
    Row(
        modifier, horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(
                id = R.dimen.middle_margin
            )
        )
    ) {
        Row(Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(
                id = R.dimen.small_margin
            )
        ), verticalAlignment = Alignment.CenterVertically) {

            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Headline(text = state.title)
                TextIcon(text = state.destination, iconRes = R.drawable.ic_globe)

                FlowRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    TextIcon(state.packingLists.size.toString(), R.drawable.ic_checklist)
                    TextIcon(state.events.size.toString(), R.drawable.ic_event)
                    TextIcon(state.tickets.size.toString(), R.drawable.ic_ticket)
                }
            }

            Column(Modifier.width(IntrinsicSize.Min)) {
                BodyText(text = state.startDate.toShortString)
                HorizontalDivider()
                BodyText(text = state.endDate.toShortString)
            }
        }

        ActionMenu(actions = actions, item = state)
    }
}

@Preview
@Composable
fun TicketElement(
    state: TicketState =
        TicketState(
            fileUrl = "",
            LocalDateTime.now(),
            "Train to Kyiv",
            true,
            ticketType = TicketDto.TicketType.TRAIN
        ),
    modifier: Modifier = Modifier.padding(16.dp)
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Headline(text = state.caption)
        TextIcon(text = state.dateTime.formatString, iconRes = R.drawable.ic_calendar)
    }
}


@Preview
@Composable
fun PlaceElement(
    state: PlaceState =
        PlaceState("Kyiv, Ukraine"),
    modifier: Modifier = Modifier.padding(16.dp)
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Headline(text = state.name)
    }
}


@Preview
@Composable
fun EventElement(
    state: EventState =
        EventState(
            "Visiting museum",
            LocalDateTime.now(),
            "Can't wait to finally see Mona Lisa",
            address = "Louvre, Paris, France"
        ),
    modifier: Modifier = Modifier.padding(16.dp)
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Headline(text = state.title)
        state.description.takeIf { it.isNotEmpty() }?.let {
            Description(text = state.description)
        }
        TextIcon(text = state.dateTime.formatString, iconRes = R.drawable.ic_calendar)
        state.address.takeIf { it.isNotEmpty() }?.let {
            TextIcon(text = state.address, iconRes = R.drawable.ic_place)
        }
    }
}

@Preview
@Composable
fun PackingItem(
    state: PackingItemState = PackingItemState(
        "Blanket", isPacked = true
    ), modifier: Modifier = Modifier
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Checkbox(isChecked = state.isPacked)
        BodyText(text = state.name)
    }
}


@Preview
@Composable
fun PackingList(
    state: PackingListState = PackingListState(
        "Things i packed", items = listOf(),
    ), modifier: Modifier = Modifier
) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Headline(text = state.name)
        val (checked, unchecked) = state.items.partition { it.isPacked }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            TextIcon(text = checked.size.toString(), iconRes = R.drawable.ic_checkbox)
            TextIcon(text = unchecked.size.toString(), iconRes = R.drawable.ic_checkbox_blank)
        }
    }

}


@Composable
fun EventsList(
    events: List<EventState>,
    navigateToAddScreen: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        item {
            ListHeader(
                "Events",
                Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
                navigateToAddScreen
            )
        }
        itemsIndexed(items = events) { index, item ->
            val isStartOfGroup =
                index == 0 || item.dateTime.toShortString != events[index - 1].dateTime.toShortString
            item.let {
                if (isStartOfGroup) {
                    Headline(
                        text = it.dateTime.toShortString
                    )
                }
                EventElement(it)
                if (!isStartOfGroup) HorizontalDivider()
            }
        }
    }
}