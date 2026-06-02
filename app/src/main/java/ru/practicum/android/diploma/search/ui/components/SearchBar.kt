package ru.practicum.android.diploma.search.ui.components

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.AppTheme
import ru.practicum.android.diploma.core.ui.theme.Dimens
import ru.practicum.android.diploma.core.ui.utils.TextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    showClearButton: Boolean,
    onIconClicked: () -> Unit,
    @StringRes placeholderStringResource: Int? = null
) {
    TextField(
        query = query,
        onQueryChanged = onQueryChanged,
        onFocusChanged = onFocusChanged,
        modifier = Modifier
            .padding(horizontal = Dimens.padding16, vertical = Dimens.padding8),
        trailingIconId = if (showClearButton) {
            R.drawable.ic_search_close
        } else {
            R.drawable.ic_search_lens
        },
        onIconClick = onIconClicked,
        contentPaddings = PaddingValues(
            start = Dimens.padding16,
            top = Dimens.padding16,
            end = Dimens.padding16,
            bottom = Dimens.padding16
        ),
        placeholderStringResource = placeholderStringResource
    )
}

@Preview(name = "Light", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun SearchScreenPreview() {
    AppTheme {
        Column {
            SearchBar(
                query = "124",
                onQueryChanged = {},
                onFocusChanged = {},
                showClearButton = true,
                onIconClicked = { }
            )

            SearchBar(
                query = "",
                onQueryChanged = {},
                onFocusChanged = {},
                showClearButton = false,
                onIconClicked = {}
            )
        }
    }
}
