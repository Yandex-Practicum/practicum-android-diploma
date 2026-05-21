package ru.practicum.android.diploma.search.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.AppTheme
import ru.practicum.android.diploma.core.ui.theme.Blue
import ru.practicum.android.diploma.core.ui.theme.Dimens
import ru.practicum.android.diploma.core.ui.theme.WhiteUniversal
import ru.practicum.android.diploma.core.ui.utils.AppScreen
import ru.practicum.android.diploma.core.ui.utils.LoadingContent
import ru.practicum.android.diploma.core.ui.utils.Spacer
import ru.practicum.android.diploma.core.ui.utils.Stub
import ru.practicum.android.diploma.core.ui.utils.VacancyList
import ru.practicum.android.diploma.search.ui.components.SearchBar
import ru.practicum.android.diploma.search.ui.mock.SearchPreviewProvider

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onNavigateToFilter: () -> Unit,
    onNavigateToVacancy: (id: String) -> Unit
) {
    val state = viewModel.state.collectAsState()
    val isFiltered = viewModel.isFiltered.collectAsState()
    val showClearButton = viewModel.showClearButton.collectAsState()

    AppScreen(
        R.string.search_screen_title,
        actions = {
            SearchFilterIcon(isFiltered.value, onNavigateToFilter)
        }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            SearchBar(
                query = viewModel.query.value,
                onFocusChanged = viewModel::onFocusChanged,
                onIconClicked = viewModel::onSearchIconClicked,
                showClearButton = showClearButton.value,
                onQueryChanged = viewModel::onQueryChanged
            )
            SearchContent(state.value, onNavigateToVacancy)
        }
    }
}

@Composable
private fun SearchFilterIcon(isFiltered: Boolean, onNavigateToFilter: () -> Unit) {
    IconButton(onClick = onNavigateToFilter) {
        Icon(
            painter = painterResource(
                id = if (isFiltered) {
                    R.drawable.ic_search_filter_on
                } else {
                    R.drawable.ic_search_filter_off
                }
            ),
            contentDescription = null,
            tint = if (isFiltered) {
                Color.Unspecified
            } else {
                MaterialTheme.colorScheme.onBackground
            },
            modifier = Modifier
        )
    }
}
@Composable
private fun SearchContent(state: SearchViewState, onNavigateToVacancy: (String) -> Unit) {
    when (state) {
        is SearchViewState.Default -> {
            Stub(R.drawable.image_search_stub_default)
        }
        is SearchViewState.Loading -> LoadingContent()

        is SearchViewState.Data -> {
            val content = state.vacancies
            Chip(content.count())
            VacancyList(
                content,
                modifier = Modifier.padding(top = Dimens.padding8)
            ) { vacancy ->
                onNavigateToVacancy(vacancy.id)
            }
        }
        is SearchViewState.Error -> {
            val error = state.error
            when (error) {
                SearchViewError.Internet -> {
                    Stub(
                        R.drawable.image_core_stub_no_internet,
                        R.string.search_error_internet
                    )
                }
                SearchViewError.NotFound -> {
                    Chip(0)
                    Spacer(height = Dimens.padding8)
                    Stub(
                        R.drawable.image_core_stub_not_found,
                        R.string.search_error_not_found
                    )
                }
            }
        }
    }
}

@Composable
private fun Chip(count: Int) {
    Surface(
        shape = RoundedCornerShape(Dimens.radius12),
        color = Blue,
        modifier = Modifier,

    ) {
        Text(
            text = pluralStringResource(
                R.plurals.search_chip_found,
                count,
                count
            ),
            style = MaterialTheme.typography.bodyMedium,
            color = WhiteUniversal,
            modifier = Modifier.padding(horizontal = Dimens.padding12, vertical = Dimens.padding4)
        )

    }
}

@Preview(name = "Light", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun SearchScreenPreview(
    @PreviewParameter(SearchPreviewProvider::class) model: SearchViewModel
) {
    AppTheme {
        SearchScreen(
            model,
            onNavigateToFilter = {},
            onNavigateToVacancy = {}
        )
    }
}
