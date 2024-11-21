package com.mrdor1stan.buonjourney.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mrdor1stan.buonjourney.R
import com.mrdor1stan.buonjourney.common.extentions.dateFromMillis
import com.mrdor1stan.buonjourney.common.extentions.millis
import java.time.LocalDateTime


@Composable
fun Headline(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        modifier,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
fun Description(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        modifier,
        style = MaterialTheme.typography.bodyMedium.merge(fontStyle = FontStyle.Italic)
    )
}

@Composable
fun BodyText(text: String, modifier: Modifier = Modifier, maxLines: Int = Int.MAX_VALUE) {
    Text(
        text,
        modifier,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = maxLines
    )
}

@Composable
fun Icon(@DrawableRes iconRes: Int, modifier: Modifier = Modifier) {
    Icon(
        imageVector = ImageVector.vectorResource(id = iconRes), contentDescription = null, modifier
    )
}

@Composable
fun TextIcon(text: String, @DrawableRes iconRes: Int, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Icon(iconRes, Modifier.size(24.dp))
        BodyText(text)
    }
}

@Composable
fun Checkbox(isChecked: Boolean, modifier: Modifier = Modifier) {
    Icon(
        if (isChecked) R.drawable.ic_checkbox else R.drawable.ic_checkbox_blank,
        modifier = modifier
    )
}

@Composable
fun ImageButton(
    iconRes: Int,
    size: Dp = 36.dp,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Icon(
        iconRes = iconRes,
        modifier
            .size(size)
            .clickable(onClick = onClick)
    )
}

@Composable
fun ListHeader(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Headline(text = text, modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(16.dp))
        ImageButton(iconRes = R.drawable.ic_add, onClick = onClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    onSuccess: (LocalDateTime) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    currentDate: LocalDateTime? = null
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = (currentDate ?: LocalDateTime.now()).millis
    )
    val confirmEnabled = remember {
        derivedStateOf {
            datePickerState.selectedDateMillis != null
        }
    }
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val date =
                        datePickerState.selectedDateMillis?.let { onSuccess(it.dateFromMillis) }

                },
                enabled = confirmEnabled.value
            ) {
                Text(text = "OK")
            }
        }
    ) {
        androidx.compose.material3.DatePicker(state = datePickerState)
    }
}

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit, enabled: Boolean) {
    BodyText(text = text,
        Modifier
            .run {
                if (enabled)
                    background(Color.Magenta).clickable(onClick = onClick)
                else
                    background(Color.Gray)
            }
            .height(36.dp))
}


@Composable
fun <T> ListWithHeader(
    header: String,
    items: List<T>,
    modifier: Modifier = Modifier,
    navigateToAddScreen: () -> Unit,
    itemContent: @Composable (T) -> Unit
) {
    LazyColumn(modifier) {
        item {
            ListHeader(
                header,
                Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
                navigateToAddScreen
            )
        }
        items(items = items) {
            itemContent(it)
        }
    }
}