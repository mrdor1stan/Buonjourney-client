package com.mrdor1stan.buonjourney.ui.common

import android.media.audiofx.AudioEffect.Descriptor
import android.widget.ImageButton
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mrdor1stan.buonjourney.R


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
        style = MaterialTheme.typography.bodyMedium.merge(Color.White),
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
fun ImageButton(iconRes: Int, size: Dp = 36.dp, onClick: () -> Unit, modifier: Modifier = Modifier) {
   Icon(iconRes = iconRes, modifier.size(size).clickable(onClick = onClick))
}