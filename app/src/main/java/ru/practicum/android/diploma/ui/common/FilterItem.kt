package ru.practicum.android.diploma.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R

@Composable
fun FilterItem(
    headlineText: String,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
) {
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        headlineContent = {
            if (supportingText == null) {
                Text(
                    text = headlineText,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        overlineContent = {
            if (supportingText != null) {
                Text(
                    text = headlineText,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        },
        supportingContent = {
            if (supportingText != null) {
                Text(
                    text = supportingText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        },
        trailingContent = {
            val iconModifier = Modifier.size(14.dp)
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.Center,
            ) {
                if (supportingText != null) {
                    Icon(
                        modifier = iconModifier,
                        painter = painterResource(R.drawable.ic_cross),
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = null,
                    )
                } else {
                    Icon(
                        modifier = iconModifier.width(8.dp),
                        painter = painterResource(R.drawable.ic_arrow_right),
                        tint = MaterialTheme.colorScheme.onBackground,
                        contentDescription = null,
                    )
                }
            }
        },
    )
}
