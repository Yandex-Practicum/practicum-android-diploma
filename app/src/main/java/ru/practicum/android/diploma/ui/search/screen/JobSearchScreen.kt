package ru.practicum.android.diploma.ui.search.screen

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.search.state.JobSearchState
import ru.practicum.android.diploma.ui.common.BadgeItem
import ru.practicum.android.diploma.ui.common.IconResource
import ru.practicum.android.diploma.ui.common.PlaceholderLayout
import ru.practicum.android.diploma.ui.common.SearchQueryField
import ru.practicum.android.diploma.ui.common.TopBar
import ru.practicum.android.diploma.ui.common.search.VacanciesContent
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun JobSearchScreen(
    state: JobSearchState,
    searchQuery: String,
    hasActiveFilter: Boolean,
    onVacancyClick: (String) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onClear: () -> Unit,
    onLoadNextPage: () -> Unit,
    onFilterClick: () -> Unit,
    onNetworkError: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        modifier = Modifier.testTag(SearchScreenTestTags.Container),
        topBar = {
            TopBar(
                text = stringResource(R.string.search_screen_title),
                navIconVisible = false,
                endFirstIconVisible = true,
                endFirstIcon = IconResource.FilterIcon(
                    isActive = hasActiveFilter,
                    onClick = onFilterClick,
                    defaultColor = MaterialTheme.colorScheme.onBackground
                ),
                endSecondIconVisible = false
            )
        },
        contentWindowInsets = WindowInsets(bottom = 0),
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            SearchQueryField(
                searchQuery = searchQuery,
                hintRes = R.string.search_input_hint,
                interactionSource = interactionSource,
                onSearchTextChange = onSearchTextChange,
                onClear = onClear,
                onKeyboardDone = { keyboardController?.hide() },
                textFieldModifier = Modifier.testTag(SearchScreenTestTags.TextField),
            )
            Box(modifier = Modifier.weight(1F)) {
                JobSearchStateContent(
                    state = state,
                    onVacancyClick = onVacancyClick,
                    onLoadNextPage = onLoadNextPage,
                )
            }
        }
    }
}

@Composable
private fun JobSearchStateContent(
    state: JobSearchState,
    onVacancyClick: (String) -> Unit,
    onLoadNextPage: () -> Unit,
) {
    when (state) {
        is JobSearchState.Content -> VacanciesContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = Dimens.ScreenHorizontalPadding,
                    top = 8.dp,
                    end = Dimens.ScreenHorizontalPadding,
                ),
            vacancies = state.vacancies,
            vacancyAmount = state.found,
            isLoading = state.isLoading,
            onVacancyClick = onVacancyClick,
            onLoadNextPage = onLoadNextPage
        )

        JobSearchState.Initial -> {
            PlaceholderLayout(R.drawable.img_search_initial_placeholder)
        }

        JobSearchState.Empty -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BadgeItem(
                    modifier = Modifier.padding(top = 12.dp),
                    vacancyAmount = 0
                )
                PlaceholderLayout(
                    R.drawable.img_nothing_found,
                    R.string.no_vacancies_error
                )
            }
        }

        JobSearchState.Error -> {
            PlaceholderLayout(
                R.drawable.img_no_internet,
                R.string.no_internet_error
            )
        }
    }
}

data object SearchScreenTestTags {
    const val Container = "SearchScreen"
    const val TextField = "${Container}_TextField"
    const val ClearButton = "${Container}_ClearButton"
    const val VacanciesList = "${Container}_VacanciesList"
}
