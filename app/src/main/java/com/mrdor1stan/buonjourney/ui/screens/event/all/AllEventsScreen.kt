package com.mrdor1stan.buonjourney.ui.screens.event.all

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.ui.common.ActionState
import com.mrdor1stan.buonjourney.ui.common.EventElement
import com.mrdor1stan.buonjourney.ui.common.ItemsListWithHeader
import kotlinx.coroutines.launch

@Composable
fun AllEventsScreen(
    tripId: Long?,
    viewModel: AllEventsScreenViewModel = viewModel(
        factory = AllEventsScreenViewModel.Factory(
            tripId
        )
    ),
    modifier: Modifier = Modifier,
    navigateToAddScreen: (eventId: Long?) -> Unit,
    navigateToItem: (eventId: Long) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    ItemsListWithHeader(
        header = "Events",
        modifier = modifier,
        items = state.results,
        navigateToAddScreen = { navigateToAddScreen(null) },
        emptyListMessage = "No events were created",
        emptyListButtonText = "Create first event",
        actions = listOf(
            ActionState(
                stringResource(id = R.string.delete_button),
                Icons.Default.Delete
            ) { scope.launch { viewModel.deleteEvent(it) } },
            ActionState(
                stringResource(id = R.string.edit_button),
                Icons.Default.Edit
            ) { navigateToAddScreen(it) },

            )
    ) { item, actions ->
        EventElement(item, actions = actions,  modifier = Modifier
            .clickable { item.id?.let { navigateToItem(it) } }
            .padding(16.dp))
    }

}