package ru.practicum.android.diploma.ui.common.industry

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.common.IconImage
import ru.practicum.android.diploma.ui.common.IconResource
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun IndustryItem(
    modifier: Modifier = Modifier,
    text: String,
    checked: Boolean,
    onItemClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable(onClick = onItemClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = Dimens.ScreenHorizontalPadding),
            text = text,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        IconImage(
            modifier = Modifier.padding(end = 4.dp),
            icon = IconResource.DefaultIcon(
                resId = if (checked) {
                    R.drawable.ic_round_checked
                } else {
                    R.drawable.ic_round_unchecked
                },
                onClick = onItemClick,
                color = MaterialTheme.colorScheme.primary,
            ),
        )
    }
}
