package com.mrdor1stan.buonjourney.ui.screens.trip.all

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
import com.mrdor1stan.buonjourney.ui.common.ItemsListWithHeader
import com.mrdor1stan.buonjourney.ui.common.TripElement
import kotlinx.coroutines.launch

@Composable
fun AllTripsScreen(
    viewModel: AllTripsScreenViewModel = viewModel(factory = AllTripsScreenViewModel.Factory),
    modifier: Modifier = Modifier,
    navigateToAddScreen: (Long?) -> Unit,
    navigateToItem: (Long) -> Unit,
) {
    val state by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    ItemsListWithHeader(
        header = "All trips",
        modifier = modifier,
        actions = listOf(
            ActionState(
                stringResource(id = R.string.delete_button),
                Icons.Default.Delete
            ) { scope.launch { viewModel.deleteTrip(it) } },
            ActionState(
                stringResource(id = R.string.edit_button),
                Icons.Default.Edit
            ) { navigateToAddScreen(it) },

            ),
        items = state.results,
        navigateToAddScreen = { navigateToAddScreen(null) },
    ) { item, actions ->
        TripElement(
            item,
            Modifier
                .clickable { item.id?.let { navigateToItem(it) } }
                .padding(16.dp),
            actions = actions
        )
    }
}