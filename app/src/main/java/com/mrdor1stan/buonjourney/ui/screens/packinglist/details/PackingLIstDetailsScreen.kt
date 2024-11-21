package com.mrdor1stan.buonjourney.ui.screens.packinglist.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.ui.common.Loader
import com.mrdor1stan.buonjourney.ui.common.PackingItem
import com.mrdor1stan.buonjourney.ui.common.PackingList
import com.mrdor1stan.buonjourney.ui.common.PrimaryButton
import com.mrdor1stan.buonjourney.ui.entities.PackingItemState
import kotlinx.coroutines.launch

@Composable
fun PackingListDetailsScreen(
    listId: Long,
    viewModel: PackingListDetailsScreenViewModel = viewModel(
        factory = PackingListDetailsScreenViewModel.Factory(
            listId
        )
    ),
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val state by viewModel.uiState.collectAsState()
    when (val list = state.packingList) {
        null -> Loader(modifier)
        else -> Column(modifier) {
            Row {
                TextField(value = state.input, onValueChange = viewModel::updateInput)
                PrimaryButton(text = "Add", onClick = {
                    scope.launch {
                        viewModel.addItemToList()
                    }
                }, enabled = state.isAddButtonEnabled)
            }
            LazyColumn {
                item {
                    PackingList(list)
                }
                items(items = list.items) {
                    PackingItem(
                        state = PackingItemState(name = it.name, isPacked = it.isPacked),
                        Modifier
                            .clickable {
                                scope.launch {
                                    viewModel.updateItem(it)
                                }
                            }
                            .padding(8.dp)
                            .fillParentMaxWidth()
                    )
                }
            }
        }
    }
}