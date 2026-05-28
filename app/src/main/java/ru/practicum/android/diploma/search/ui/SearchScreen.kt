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
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel<SearchViewModelImpl>(),
    onNavigateToFilter: () -> Unit,
    onNavigateToVacancy: (id: String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isFiltered by viewModel.isFiltered.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()

    AppScreen(
        title = R.string.search_screen_title,
        actions = {
            SearchFilterIcon(isFiltered, onNavigateToFilter)
        }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            SearchBar(
                query = query,
                onFocusChanged = viewModel::onFocusChanged,
                onIconClicked = viewModel::onSearchIconClicked,
                showClearButton = query.isNotEmpty(),
                onQueryChanged = viewModel::onQueryChanged
            )
            SearchContent(state, onNavigateToVacancy)
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
private fun SearchContent(state: SearchScreenState, onNavigateToVacancy: (String) -> Unit) {
    when (state) {
        is SearchScreenState.Initial -> {
            Stub(R.drawable.image_search_stub_default)
        }
        is SearchScreenState.Loading -> {
            LoadingContent()
        }
        is SearchScreenState.Content -> {
            Chip(count = state.totalFound)
            VacancyList(
                state.vacancies,
                modifier = Modifier.padding(top = Dimens.padding8)
            ) { vacancy ->
                onNavigateToVacancy(vacancy.id)
            }
        }
        is SearchScreenState.Error -> {
            when (state.error) {
                SearchError.INTERNET -> {
                    Stub(
                        R.drawable.image_core_stub_no_internet,
                        R.string.search_error_internet
                    )
                }
                SearchError.NOT_FOUND -> {
                    Chip(count = 0)
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
