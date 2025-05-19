package com.mrdor1stan.buonjourney.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.semantics.CollectionInfo
import androidx.compose.ui.semantics.CollectionItemInfo
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.collectionInfo
import androidx.compose.ui.semantics.collectionItemInfo
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.ui.entities.DataState

@Composable
fun <IdType, StateType: DataState<IdType>> ItemsListWithHeader(
    header: String,
    items: List<StateType>,
    actions: List<ActionState<IdType>> = listOf(),
    modifier: Modifier = Modifier,
    navigateToAddScreen: (() -> Unit)?,
    itemContent: @Composable (StateType, List<ActionState<IdType>>) -> Unit
) {
    LazyColumn(modifier, verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_margin))) {
        item {
            ListHeader(
                header,
                Modifier
                    .padding(horizontal = 12.dp, vertical = 16.dp)
                    .semantics {
                        collectionInfo = CollectionInfo(
                            rowCount = items.count(),
                            columnCount = 1
                        )
                    }
                ,
                navigateToAddScreen
            )
        }
        itemsIndexed(items = items) { index, item ->
            Box(
                Modifier
                    .fillParentMaxWidth()
                    .semantics {
                        collectionItemInfo =
                            CollectionItemInfo(index, 0, 0, 0)
                        customActions = actions.map { action ->
                            CustomAccessibilityAction(
                                label = action.label,
                                action = { item.id?.let { action.onClick(it) }; true }
                            )
                        }
                    }) {
                itemContent(item, actions)
            }
        }
    }
}

//@Composable
//fun EmptyListPlaceholder(modifier: Modifier = Modifier) {
//    ResourceIcon(iconRes = )
//}