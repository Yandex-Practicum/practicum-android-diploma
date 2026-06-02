package ru.practicum.android.diploma.region.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.core.ui.theme.AppTheme
import ru.practicum.android.diploma.core.ui.theme.Dimens
import ru.practicum.android.diploma.core.ui.utils.AppScreen
import ru.practicum.android.diploma.core.ui.utils.ListItem
import ru.practicum.android.diploma.core.ui.utils.LoadingContent
import ru.practicum.android.diploma.core.ui.utils.Stub
import ru.practicum.android.diploma.region.ui.mock.RegionPreviewProvider
import ru.practicum.android.diploma.search.ui.components.SearchBar

@Composable
fun RegionScreen(
    viewModel: RegionViewModel,
    onSelect: (region: Area, country: Area) -> Unit,
    onBack: () -> Unit
) {
    val query by viewModel.query.collectAsState()
    val state by viewModel.state.collectAsState()

    AppScreen(R.string.region_screen_title, onBack) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            SearchBar(
                query = query,
                onQueryChanged = viewModel::onQueryChanged,
                onFocusChanged = {},
                showClearButton = query.isNotEmpty(),
                onIconClicked = viewModel::onSearchIconClicked,
                placeholderStringResource = R.string.region_search_placeholder
            )
            when (state) {
                is RegionScreenState.Loading -> LoadingContent()

                is RegionScreenState.Content -> {
                    LazyColumn(modifier = Modifier.padding(top = Dimens.padding8)) {
                        items((state as RegionScreenState.Content).regions, key = { it.region.id }) { item ->
                            ListItem(
                                title = item.region.name,
                                icon = R.drawable.ic_core_arrow_forward,
                                onClickItem = { onSelect(item.region, item.country) }
                            )
                        }
                    }
                }

                is RegionScreenState.Empty -> Stub(
                    R.drawable.image_core_stub_not_found,
                    R.string.region_stub_nothing_found
                )

                is RegionScreenState.Error -> {
                    val error = (state as RegionScreenState.Error).error
                    Stub(
                        if (error == RegionError.NO_INTERNET) {
                            R.drawable.image_core_stub_no_internet
                        } else {
                            R.drawable.image_core_stub_empty_list
                        },
                        if (error == RegionError.NO_INTERNET) {
                            R.string.no_internet_connection
                        } else {
                            R.string.region_stub_empty_list
                        }
                    )
                }
            }
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun RegionScreenPreview(
    @PreviewParameter(RegionPreviewProvider::class) model: RegionViewModel
) {
    AppTheme {
        RegionScreen(model, { _, _ -> }, {})
    }
}
