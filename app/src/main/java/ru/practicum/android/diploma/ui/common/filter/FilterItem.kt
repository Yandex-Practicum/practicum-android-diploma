package ru.practicum.android.diploma.ui.common.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun FilterItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String? = null,
    headlineContentColorInverted: Boolean = false,
    onItemClick: () -> Unit = {},
    onCrossClick: () -> Unit = {}
) {
    val hasValue = value != null
    val headlineColor = if (headlineContentColorInverted) {
        MaterialTheme.colorScheme.onBackground
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    val iconImg = if (hasValue) {
        painterResource(R.drawable.ic_cross)
    } else {
        painterResource(R.drawable.ic_arrow_forward_14)
    }

    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable(
                onClick = onItemClick
            ),
        headlineContent = {
            if (!hasValue) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = headlineColor
                )
            }
        },
        overlineContent = {
            if (hasValue) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        supportingContent = {
            if (hasValue) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        },
        trailingContent = {
            IconButton(
                modifier = Modifier.wrapContentSize(),
                onClick = if (hasValue) {
                    onCrossClick
                } else {
                    onItemClick
                }
            ) {
                Icon(
                    modifier = Modifier
                        .size(14.dp),
                    painter = iconImg,
                    tint = MaterialTheme.colorScheme.onBackground,
                    contentDescription = null,
                )
            }
        },
    )
}

private const val COUNTRY_RUSSIA = "Россия"
private const val COUNTRY = "Страна"

@Preview(showSystemUi = false)
@Composable
private fun FilterItemPreview() {
    AppTheme {
        Column {
            FilterItem(title = COUNTRY, value = COUNTRY_RUSSIA)
            FilterItem(title = COUNTRY, headlineContentColorInverted = true)
        }
    }
}
