package ru.practicum.android.diploma.ui.search.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.search.state.JobSearchState
import ru.practicum.android.diploma.ui.common.BadgeItem
import ru.practicum.android.diploma.ui.common.IconImage
import ru.practicum.android.diploma.ui.common.PlaceholderLayout
import ru.practicum.android.diploma.ui.common.TopBar
import ru.practicum.android.diploma.ui.common.search.VacanciesContent
import ru.practicum.android.diploma.ui.theme.Blue
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun JobSearchScreen(
    state: JobSearchState,
    searchQuery: String,
    onVacancyClick: (String) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onClear: () -> Unit,
    onLoadNextPage: () -> Unit,
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
                endFirstIconId = R.drawable.ic_filter,
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
                interactionSource = interactionSource,
                onSearchTextChange = onSearchTextChange,
                onClear = onClear,
                onKeyboardDone = { keyboardController?.hide() },
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
private fun SearchQueryField(
    searchQuery: String,
    interactionSource: MutableInteractionSource,
    onSearchTextChange: (String) -> Unit,
    onClear: () -> Unit,
    onKeyboardDone: () -> Unit,
) {
    val fieldShape = RoundedCornerShape(8.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Dimens.ScreenHorizontalPadding,
                top = 8.dp,
                end = Dimens.ScreenHorizontalPadding,
            )
            .height(56.dp)
            .clip(fieldShape)
            .background(MaterialTheme.colorScheme.surfaceContainer),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = searchQuery,
            onValueChange = onSearchTextChange,
            modifier = Modifier
                .weight(1f)
                .testTag(SearchScreenTestTags.TextField)
                .fillMaxHeight()
                .padding(start = 20.dp),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium
                .copy(color = MaterialTheme.colorScheme.secondaryFixed),
            cursorBrush = SolidColor(Blue),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { onKeyboardDone() }),
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = stringResource(R.string.search_input_hint),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.inverseOnSurface
                            ),
                            maxLines = 1,
                            modifier = Modifier.align(Alignment.CenterStart),
                        )
                    }
                    innerTextField()
                }
            }
        )
        IconImage(
            modifier = Modifier
                .testTag(SearchScreenTestTags.ClearButton)
                .padding(end = 4.dp)
                .clickable(enabled = true, onClick = onClear),
            resId = if (searchQuery.isEmpty()) R.drawable.ic_search else R.drawable.ic_cross,
            color = MaterialTheme.colorScheme.secondaryFixed
        )
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
