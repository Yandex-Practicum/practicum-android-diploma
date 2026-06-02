package ru.practicum.android.diploma.search.ui

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isFiltered by viewModel.isFiltered.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.errorCode.collect { code ->
            Toast.makeText(
                context,
                if (code == SearchError.NO_INTERNET) {
                    R.string.search_toast_error_internet
                } else {
                    R.string.search_toast_error
                },
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    AppScreen(
        R.string.search_screen_title,
        actions = { SearchFilterIcon(isFiltered, onNavigateToFilter) }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            SearchBar(
                query = query,
                onFocusChanged = {},
                onIconClicked = viewModel::onSearchIconClicked,
                showClearButton = query.isNotEmpty(),
                onQueryChanged = viewModel::onQueryChanged
            )
            SearchContent(state, viewModel::onLastItemReached, onNavigateToVacancy)
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
@Suppress("CyclomaticComplexMethod")
@Composable
private fun SearchContent(
    state: SearchScreenState,
    onLastItemReached: () -> Unit,
    onNavigateToVacancy: (String) -> Unit
) {
    val listState = rememberLazyListState()

    val isReachedBottom by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            totalItems > 0 && lastVisibleItemIndex >= totalItems - 1
        }
    }

    LaunchedEffect(isReachedBottom) {
        if (isReachedBottom) {
            onLastItemReached()
        }
    }

    LaunchedEffect(state) {
        if (state is SearchScreenState.Loading) {
            listState.scrollToItem(0)
        }
    }

    when (state) {
        is SearchScreenState.Initial -> {
            Stub(R.drawable.image_search_stub_default)
        }
        is SearchScreenState.Loading -> LoadingContent()

        is SearchScreenState.Content -> {
            Chip(count = state.totalFound)
            VacancyList(
                vacancies = state.vacancies,
                listState = listState,
                isNextPageLoading = state.isNextPageLoading,
                modifier = Modifier.padding(top = Dimens.padding8)
            ) { vacancy ->
                onNavigateToVacancy(vacancy.id)
            }
        }
        is SearchScreenState.Error -> {
            val error = state.error
            when (error) {
                SearchError.NO_INTERNET -> {
                    Stub(
                        R.drawable.image_core_stub_no_server_1,
                        R.string.search_error_internet
                    )
                }
                SearchError.SERVER_ERROR -> {
                    Stub(
                        R.drawable.image_core_stub_no_internet,
                        R.string.search_error_server
                    )
                }
                SearchError.EMPTY_RESULTS -> {
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
