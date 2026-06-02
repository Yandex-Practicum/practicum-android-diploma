package ru.practicum.android.diploma.ui.common.industry

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

@Composable
fun IndustryItem(
    modifier: Modifier = Modifier,
    text: String,
    checked: Boolean,
    onItemClick: () -> Unit = {}
) {
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable(
                onClick = onItemClick
            ),
        headlineContent = {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
        },
        trailingContent = {
            IconButton(
                modifier = Modifier.wrapContentSize(),
                onClick = onItemClick
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = if (checked) {
                        painterResource(R.drawable.ic_round_checked)
                    } else {
                        painterResource(R.drawable.ic_round_unchecked)
                    },
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null,
                )
            }
        },
    )
}

@Preview(showSystemUi = false)
@Composable
private fun IndustryItemPreview() {
    Column {
        IndustryItem(text = "Авиаперевозки", checked = true)
        IndustryItem(text = "Авиационная, вертолетная и аэрокосмическая промышленность", checked = false)

    }
}
