package com.mrdor1stan.buonjourney.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.common.extentions.formatString
import com.mrdor1stan.buonjourney.common.extentions.toShortString
import com.mrdor1stan.buonjourney.ui.entities.EventState
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

        ActionMenu(actions = actions, item = state)
    }
}

//@Preview
//@Composable
//fun EventElement(
//    state: EventState =
//        EventState(
//            "Visiting museum",
//            LocalDateTime.now(),
//            "Can't wait to finally see Mona Lisa",
//            address = "Louvre, Paris, France"
//        ),
//    modifier: Modifier = Modifier.padding(16.dp)
//) {
//    Column(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
//        Headline(text = state.title)
//        state.description.takeIf { it.isNotEmpty() }?.let {
//            Description(text = state.description)
//        }
//        TextIcon(text = state.dateTime.formatString, iconRes = R.drawable.ic_calendar)
//        state.address.takeIf { it.isNotEmpty() }?.let {
//            TextIcon(text = state.address, iconRes = R.drawable.ic_place)
//        }
//    }
//}

@Composable
fun EventsList(
    events: List<EventState>,
    navigateToAddScreen: (Long?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        item {
            ListHeader(
                "Events",
                Modifier.padding(horizontal = 12.dp, vertical = 16.dp)
            ) { navigateToAddScreen(null) }
        }
        itemsIndexed(items = events) { index, item ->
            val isStartOfGroup =
                index == 0 // || item.dateTime.toShortString != events[index - 1].dateTime.toShortString
            item.let {
                if (isStartOfGroup) {
//                    Headline(
//                        text = it.dateTime.toShortString
//                    )
                }
                //EventElement(it)
                if (!isStartOfGroup) HorizontalDivider()
            }
        }
    }
}


@Composable
fun TripDateRange(from: LocalDateTime?, to: LocalDateTime?, modifier: Modifier = Modifier) {
    Column(Modifier.width(IntrinsicSize.Max).then(modifier),
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