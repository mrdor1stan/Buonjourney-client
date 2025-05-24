package com.mrdor1stan.buonjourney.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.mrdor1stan.buonjourney.R

@Composable
fun Headline(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        modifier,
        style = MaterialTheme.typography.headlineSmall,
    )
}

@Composable
fun Title(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        modifier,
        style = MaterialTheme.typography.titleMedium,
        maxLines = 2
    )
}

@Composable
fun Description(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        modifier,
        style = MaterialTheme.typography.bodyMedium.merge(fontStyle = FontStyle.Italic),
        maxLines = 3
    )
}

@Composable
fun BodyText(text: String, modifier: Modifier = Modifier, maxLines: Int = Int.MAX_VALUE) {
    Text(
        text,
        modifier,
        style = MaterialTheme.typography.bodyMedium.merge(),
        maxLines = maxLines
    )
}

@Composable
fun ListHeader(text: String, modifier: Modifier = Modifier, onClick: (() -> Unit)?) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Headline(text = text, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(16.dp))
        onClick?.let {
            IconButton(onClick = onClick) {
                Image(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_label)
                )
            }
        }
    }
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
            .heightIn(min = 60.dp)
            .then(modifier),
        enabled = enabled
    ) {
        Headline(text = text)
    }
}


@Composable
fun Loader(modifier: Modifier = Modifier) {
    Box(Modifier
        .background(color = Color.DarkGray.copy(alpha = 0.2f))
        .fillMaxSize()
        .then(modifier)) {
        CircularProgressIndicator(
            modifier = Modifier.size(100.dp).align(Alignment.Center)
        )
    }
}

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    isSelected: Boolean = true,
    onSelectionChanged: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .then(modifier),
        tonalElevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceContainerLow
    ) {
        Row(modifier = Modifier
            .toggleable(
                role = Role.RadioButton,
                value = isSelected,
                onValueChange = {
                    onSelectionChanged()
                }
            )
        ) {
            content()
        }
    }
}


@Composable
fun <T> ChipGroup(
    modifier: Modifier = Modifier,
    items: List<T> = listOf(),
    selectedItemIndex: Int = 0,
    onSelectedChanged: (T) -> Unit = {},
    content: @Composable (T) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .then(modifier)
    ) {
        LazyRow {
            itemsIndexed(items) { index, item ->
                Chip(
                    content = { content(item) },
                    isSelected = selectedItemIndex == index,
                    onSelectionChanged = {
                        onSelectedChanged(item)
                    },
                )
            }
        }
    }
}

