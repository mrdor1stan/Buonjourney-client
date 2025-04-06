package com.mrdor1stan.buonjourney.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.vector.ImageVector
import com.mrdor1stan.buonjourney.ui.entities.DataState

data class ActionState(val label: String, val icon: ImageVector, val onClick: (Long) -> Unit)

@Composable
fun ActionMenu(modifier: Modifier = Modifier, item: DataState, actions: List<ActionState>) {

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Options")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            actions.forEach { action ->
                DropdownMenuItem(
                    text = { Text(action.label) },
                    leadingIcon = { Icon(action.icon, contentDescription = null) },
                    onClick = { item?.id?.let { itemId -> action.onClick(itemId) } }
                )
            }
            HorizontalDivider()
        }
    }

}