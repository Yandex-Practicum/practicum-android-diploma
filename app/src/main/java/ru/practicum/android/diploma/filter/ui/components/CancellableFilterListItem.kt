package ru.practicum.android.diploma.filter.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.AppTheme
import ru.practicum.android.diploma.core.ui.utils.ListItem

@Composable
fun CancellableFilterListItem(
    label: String,
    value: String?,
    onReset: () -> Unit,
    onNavigate: () -> Unit
) {
    val isActive = !value.isNullOrEmpty()
    ListItem(
        label = if (isActive) label else null,
        title = if (isActive) value else label,
        icon = if (isActive) R.drawable.ic_core_close else R.drawable.ic_core_arrow_forward,
        isActive = isActive,
        onClickItem = onNavigate,
        onClickIcon = {
            if (isActive) {
                onReset()
            } else {
                onNavigate()
            }
        }
    )

}

@Suppress("MagicNumber")
@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun CancellableFilterListItemPreview() {
    var shortloremIpsum = LoremIpsum(1).values.first()
    var longloremIpsum = LoremIpsum(10).values.first()
    AppTheme {
        Column {
            CancellableFilterListItem(stringResource(R.string.filter_industry_label), "", {}, {})
            CancellableFilterListItem(stringResource(R.string.filter_industry_label), shortloremIpsum, {}, {})
            CancellableFilterListItem(longloremIpsum, longloremIpsum, {}, {})
        }
    }
}
