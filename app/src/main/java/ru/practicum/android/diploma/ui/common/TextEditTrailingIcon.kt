package ru.practicum.android.diploma.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.ui.search.screen.SearchScreenTestTags

@Composable
fun TextEditTrailingIcon(
    iconId: Int,
    onClick: () -> Unit
) {
    IconImage(
        modifier = Modifier
            .testTag(SearchScreenTestTags.ClearButton)
            .padding(end = 4.dp)
            .clickable(enabled = true, onClick = onClick),
        IconResource.DefaultIcon(
            resId = iconId,
            color = MaterialTheme.colorScheme.secondaryFixed
        )
    )
}
