package ru.practicum.android.diploma.core.ui.utils

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.AppTheme
import ru.practicum.android.diploma.core.ui.theme.Blue
import ru.practicum.android.diploma.core.ui.theme.Dimens
import ru.practicum.android.diploma.core.ui.theme.Gray

@Composable
fun ListItem(
    title: String,
    @DrawableRes icon: Int,
    iconTint: Color = MaterialTheme.colorScheme.onBackground,
    label: String? = null,
    isActive: Boolean = true,
    onClickItem: (() -> Unit)? = null,
    onClickIcon: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                top = Dimens.padding6,
                bottom = Dimens.padding6,
                start = Dimens.padding16,
                end = Dimens.padding4
            )
            .then(
                if (onClickItem != null) Modifier.clickable { onClickItem() } else Modifier
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f)
        ) {
            label?.let {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isActive) MaterialTheme.colorScheme.onBackground else Gray,
                maxLines = 1
            )
        }
        IconButton(
            onClick = { onClickIcon?.invoke() },
            Modifier
                .size(Dimens.icon48)

        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                tint = iconTint
            )
        }
    }
}

@Suppress("MagicNumber")
@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun ListItemPreview() {
    var shortloremIpsum = LoremIpsum(1).values.first()
    var longloremIpsum = LoremIpsum(10).values.first()
    AppTheme {
        Column {
            ListItem(shortloremIpsum, R.drawable.ic_core_arrow_forward, isActive = false)
            ListItem(shortloremIpsum, R.drawable.ic_core_check_box_off, iconTint = Blue, isActive = true)
            ListItem(shortloremIpsum, R.drawable.ic_core_close, label = shortloremIpsum, isActive = true)
            ListItem(longloremIpsum, R.drawable.ic_core_close, label = longloremIpsum, isActive = true)
        }
    }
}
