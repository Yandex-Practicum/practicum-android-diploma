package ru.practicum.android.diploma.ui.common.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.common.Loader
import ru.practicum.android.diploma.ui.mocks.MocData
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun VacancyList(
    modifier: Modifier = Modifier,
    vacancies: List<Vacancy>,
    isLoading: Boolean,
    onClick: (String) -> Unit,
    onLoadNextPage: () -> Unit
) {
    val listState = rememberLazyListState()
    val shouldLoadNext = remember {
        derivedStateOf {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val totalItemsCount = listState.layoutInfo.totalItemsCount

            lastVisibleItemIndex != null && lastVisibleItemIndex >= totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadNext.value) {
        if (shouldLoadNext.value) {
            onLoadNextPage()
        }
    }

    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(top = 40.dp)
    ) {
        items(
            items = vacancies,
            key = { it.id }
        ) { vacancy ->
            VacancyItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(),
                vacancy,
                onClick = onClick
            )
        }
        item {
            if (isLoading && vacancies.isNotEmpty()) {
                Loader(
                    modifier = modifier
                        .heightIn(min = 80.dp)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun VacancyListPreview() {
    AppTheme {
        VacancyList(
            vacancies = MocData.vacancies,
            isLoading = true,
            onClick = {},
            onLoadNextPage = {}
        )
    }
}
