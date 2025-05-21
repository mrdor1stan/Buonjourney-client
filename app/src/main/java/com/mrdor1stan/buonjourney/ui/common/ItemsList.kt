package com.mrdor1stan.buonjourney.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
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
fun <IdType, StateType : DataState<IdType>> ItemsListWithHeader(
    header: String,
    items: List<StateType>,
    modifier: Modifier = Modifier,
    actions: List<ActionState<IdType>> = listOf(),
    emptyListMessage: String,
    emptyListButtonText: String?,
    navigateToAddScreen: (() -> Unit)?,
    itemContent: @Composable (StateType, List<ActionState<IdType>>) -> Unit
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_margin))
    ) {
        ListHeader(
            header,
            Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 16.dp, bottom = 8.dp)
                .semantics {
                    collectionInfo = CollectionInfo(
                        rowCount = items.count(), columnCount = 1
                    )
                }, navigateToAddScreen
        )
        HorizontalDivider(thickness = 2.dp)

        when (items.isEmpty()) {
            true -> EmptyListPlaceholder(
                modifier = Modifier.fillMaxSize(),
                navigateToAddScreen = navigateToAddScreen,
                messageText = emptyListMessage,
                buttonText = emptyListButtonText
            )

            false -> LazyColumn(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small_margin))
            ) {
                itemsIndexed(items = items) { index, item ->
                    Box(
                        Modifier
                            .fillParentMaxWidth()
                            .semantics {
                                collectionItemInfo = CollectionItemInfo(index, 0, 0, 0)
                                customActions = actions.map { action ->
                                    CustomAccessibilityAction(label = action.label,
                                        action = { item.id?.let { action.onClick(it) }; true })
                                }
                            }) {
                        itemContent(item, actions)
                        HorizontalDivider(Modifier.align(Alignment.BottomCenter))
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyListPlaceholder(
    modifier: Modifier = Modifier,
    navigateToAddScreen: (() -> Unit)?,
    messageText: String,
    buttonText: String?
) {
    Box(modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.middle_margin)),
            modifier = Modifier.align(Alignment.Center)
        ) {

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.image_empty_box),
                contentDescription = null,
                Modifier.size(144.dp)
            )
            Title(text = messageText)
            if (buttonText != null && navigateToAddScreen != null) PrimaryButton(
                text = buttonText, onClick = navigateToAddScreen, enabled = true
            )
        }
    }
}

