package com.mrdor1stan.buonjourney.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.mrdor1stan.buonjourney.ui.entities.DataState

@Composable
fun <T: DataState> ItemsListWithHeader(
    header: String,
    items: List<T>,
    actions: List<ActionState> = DEFAULT_ACTIONS,
    modifier: Modifier = Modifier,
    navigateToAddScreen: () -> Unit,
    itemContent: @Composable (T, List<ActionState>) -> Unit
) {
    LazyColumn(modifier) {
        item {
            ListHeader(
                header,
                Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
                navigateToAddScreen
            )
        }
        items(items = items) { item ->
            Box(
                Modifier
                    .fillParentMaxWidth()
                    .semantics {
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
