package com.mrdor1stan.buonjourney.ui.screens.places.all

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.ui.common.ActionState
import com.mrdor1stan.buonjourney.ui.common.CityElement
import com.mrdor1stan.buonjourney.ui.common.ItemsListWithHeader
import kotlinx.coroutines.launch

@Composable
fun AllCitiesScreen(
    viewModel: AllCitiesScreenViewModel = viewModel(factory = AllCitiesScreenViewModel.Factory),
    modifier: Modifier = Modifier,
    navigateToAddScreen: (String?) -> Unit,
    navigateToItem: (Long) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    ItemsListWithHeader(
        header = "All cities",
        items = state.results,
        navigateToAddScreen = { navigateToAddScreen(null) },
        actions = listOf(
            ActionState(
                stringResource(id = R.string.delete_button),
                Icons.Default.Delete
            ) { scope.launch { viewModel.deleteCity(it) } },
            ActionState(
                stringResource(id = R.string.edit_button),
                Icons.Default.Edit
            ) { navigateToAddScreen(it) },
            ),
    ) { item, actions ->
        CityElement(state = item, actions = actions)
    }
}