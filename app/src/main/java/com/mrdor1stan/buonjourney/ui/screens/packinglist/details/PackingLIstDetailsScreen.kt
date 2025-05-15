package com.mrdor1stan.buonjourney.ui.screens.packinglist.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.data.db.PackingListNodeDto
import com.mrdor1stan.buonjourney.ui.common.BodyText
import com.mrdor1stan.buonjourney.ui.common.ChipGroup
import com.mrdor1stan.buonjourney.ui.common.Loader
import com.mrdor1stan.buonjourney.ui.common.Title
import kotlinx.coroutines.launch

@Composable
fun PackingListDetailsScreen(
    tripId: Long, viewModel: PackingListDetailsScreenViewModel = viewModel(
        factory = PackingListDetailsScreenViewModel.Factory(
            tripId
        )
    ), modifier: Modifier = Modifier
) {
    var animateScrollToLastAddedItem by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val state by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(animateScrollToLastAddedItem) {
        val index = state.packingList?.indexOfFirst { it == state.packingList?.maxBy { it.id } }
        index.takeIf { animateScrollToLastAddedItem }?.let {
            listState.animateScrollToItem(it)
            animateScrollToLastAddedItem = false
        }
    }

    when (val list = state.packingList) {
        null -> Loader(modifier)
        else -> Column(modifier) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        horizontal =
                        dimensionResource(id = R.dimen.middle_margin)
                    )
                    .padding(top = dimensionResource(id = R.dimen.small_margin))
            ) {
                TextField(
                    value = state.input,
                    onValueChange = viewModel::updateInput,
                    modifier = Modifier.weight(1f),
                    placeholder = {Text("Enter item or category")}
                )
                IconButton(
                    onClick = {
                        scope.launch {
                            viewModel.addItemToList(itemType = PackingListNodeDto.Type.ListItem)
                            animateScrollToLastAddedItem = true
                        }
                    },
                    enabled = state.isAddButtonEnabled
                ) {
                    Icon(
                        Icons.Default.PostAdd,
                        contentDescription = stringResource(R.string.add_list_item_button)
                    )
                }
                IconButton(
                    onClick = {
                        scope.launch {
                            viewModel.addItemToList(itemType = PackingListNodeDto.Type.Header)
                            animateScrollToLastAddedItem = true
                        }
                    },
                    enabled = state.isAddButtonEnabled
                ) {
                    Icon(
                        Icons.Default.CreateNewFolder,
                        contentDescription = stringResource(R.string.add_category_button)
                    )
                }
            }
            ChipGroup(
                items = PackingListScreenMode.entries,
                selectedItemIndex = state.selectedListScreenMode.ordinal,
                onSelectedChanged = viewModel::selectListMode
            ) {
                Row(
                    Modifier
                        .padding(dimensionResource(id = R.dimen.small_margin))
                        .semantics(mergeDescendants = true) {},
                    horizontalArrangement = Arrangement.spacedBy(
                        dimensionResource(id = R.dimen.small_margin)
                    )
                ) {
                    Icon(imageVector = it.icon(), contentDescription = null)
                    BodyText(text = stringResource(id = it.titleResId()))
                }
            }

            LazyColumn(state = listState) {
                itemsIndexed(items = list) { index, item ->
                    val isCheckboxSelected =
                        if (item.nodeType == PackingListNodeDto.Type.Header)
                            mutableListOf<PackingListNodeDto>()
                                .also {
                                    for (i in index + 1..list.lastIndex) {
                                        val currentItem = list[i]
                                        when (currentItem.nodeType) {
                                            PackingListNodeDto.Type.Header -> break
                                            PackingListNodeDto.Type.ListItem -> it.add(currentItem)
                                        }
                                    }
                                }.all { it.isPacked }
                        else item.isPacked

                    PackingListItem(
                        PackingListItemUiState(
                            item = item,
                            isCheckboxSelected = isCheckboxSelected,
                            isEditable = state.editedItemIndex == index,
                            showEditButtons = state.editedItemIndex == null,
                            isMovableUpward = item.ordinal > 0,
                            isMovableDownward = item.ordinal < list.lastIndex,
                            screenMode = state.selectedListScreenMode
                        ),
                        modifier = Modifier
                            .fillParentMaxWidth()
                            .padding(
                                start = dimensionResource(id = R.dimen.small_margin),
                                end = dimensionResource(id = R.dimen.small_margin),
                            ),
                        onItemPacked = {
                            viewModel.onItemPacked(item)
                        },
                        onItemMovedDownward = {
                            viewModel.onItemMoved(index = item.ordinal, moveUpward = false)
                        },
                        onItemMovedUpward = {
                            viewModel.onItemMoved(index = item.ordinal, moveUpward = true)
                        },
                        onItemRenamed = { newName ->
                            viewModel.onItemRenamed(item, newName = newName)
                        },
                        onItemRemoved = {
                            viewModel.onItemRemoved(item)
                        },
                        onEditItem = {
                            viewModel.onEditItem(index = index)
                        },
                        onCancelEditing = {
                            viewModel.onEditItem(index = null)
                        }
                    )
                }
            }
        }
    }
}

data class PackingListItemUiState(
    val item: PackingListNodeDto,
    val isEditable: Boolean,
    val isCheckboxSelected: Boolean,
    val showEditButtons: Boolean,
    val isMovableUpward: Boolean,
    val isMovableDownward: Boolean,
    val screenMode: PackingListScreenMode,
)

@Composable
private fun PackingListItem(
    state: PackingListItemUiState,
    modifier: Modifier = Modifier,
    onItemPacked: (Boolean) -> Unit,
    onItemMovedUpward: () -> Unit,
    onItemMovedDownward: () -> Unit,
    onItemRenamed: (String) -> Unit,
    onItemRemoved: () -> Unit,
    onEditItem: () -> Unit,
    onCancelEditing: () -> Unit
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_gap))
    ) {
        when (state.screenMode) {
            PackingListScreenMode.Check -> {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = state.isCheckboxSelected,
                        onCheckedChange = onItemPacked,
                        enabled = state.item.nodeType == PackingListNodeDto.Type.ListItem
                    )
                    PackingListItemText(state.item)
                }
            }

            PackingListScreenMode.Reorder -> {
                PackingListItemText(state.item, modifier = Modifier.weight(1f))
                Row {
                    IconButton(onClick = onItemMovedUpward, enabled = state.isMovableUpward) {
                        Icon(
                            imageVector = Icons.Default.ArrowUpward,
                            contentDescription = stringResource(
                                R.string.move_item_upward
                            )
                        )
                    }
                    IconButton(onClick = onItemMovedDownward, enabled = state.isMovableDownward) {
                        Icon(
                            imageVector = Icons.Default.ArrowDownward,
                            contentDescription = stringResource(
                                R.string.move_item_downward
                            )
                        )
                    }
                }
            }

            PackingListScreenMode.Edit -> {
                var textInput by remember { mutableStateOf(state.item.name) }

                when (state.isEditable) {
                    true -> {
                        TextField(
                            singleLine = true,
                            placeholder = { Text(text = stringResource(R.string.enter_new_name)) },
                            value = textInput,
                            onValueChange = { textInput = it },
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                onItemRenamed(textInput)
                                onCancelEditing()
                            },
                            enabled = textInput.isNotEmpty()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Save,
                                contentDescription = stringResource(
                                    R.string.save_button_label
                                )
                            )
                        }
                        IconButton(
                            onClick = onCancelEditing
                        ) {
                            Icon(
                                imageVector = Icons.Default.Cancel,
                                contentDescription = stringResource(
                                    R.string.cancel_button_label
                                )
                            )
                        }
                    }

                    false -> {
                        IconButton(onClick = onEditItem, enabled = state.showEditButtons) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = stringResource(
                                    R.string.rename_item
                                )
                            )
                        }
                        PackingListItemText(state.item, modifier = Modifier.weight(1f))
                        IconButton(onClick = onItemRemoved, enabled = state.showEditButtons) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(
                                    R.string.remove_item
                                )
                            )
                        }
                    }
                }

            }
        }

    }
}

@Composable
private fun PackingListItemText(state: PackingListNodeDto, modifier: Modifier = Modifier) {


    when (state.nodeType) {
        PackingListNodeDto.Type.ListItem -> BodyText(text = state.name, modifier = modifier)
        PackingListNodeDto.Type.Header -> Title(text = state.name, modifier = modifier)
    }
}
