package ru.practicum.android.diploma.core.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.core.presentation.ui.model.VacancyListItemUi
import ru.practicum.android.diploma.core.presentation.ui.theme.VacancySearchAppTheme
import ru.practicum.android.diploma.core.presentation.ui.util.formatSalary

@Composable
fun VacanciesList(
    vacancies: List<VacancyListItemUi>,
    modifier: Modifier = Modifier,
    onVacancyClick: (String) -> Unit,
    onLoadNextPage: () -> Unit = {},
    isLoading: Boolean = false
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
        state = listState,
        modifier = modifier.fillMaxSize(),
    ) {
        items(
            items = vacancies,
            key = { it.id },
        ) { vacancy ->
            VacancyItem(
                id = vacancy.id,
                logoUrl = vacancy.logoUrl,
                vacancyName = vacancy.vacancyName,
                city = vacancy.city,
                employer = vacancy.employer,
                salary = vacancy.salary,
                onClick = onVacancyClick,
            )
        }
        if (isLoading) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Loading(Modifier.size(80.dp))
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun VacanciesListPreview() {
    val sampleVacancies = listOf(
        VacancyListItemUi(
            id = "1",
            logoUrl = null,
            vacancyName = "Java-разработчик",
            city = "Омск",
            employer = "Авто.ру",
            salary = formatSalary(2_000, 5_000, "EUR"),
        ),
        VacancyListItemUi(
            id = "2",
            logoUrl = null,
            vacancyName = "Android-разработчик",
            city = "Санкт-Петербург",
            employer = "Яндекс",
            salary = formatSalary(null, 300_000, "RUB"),
        ),
        VacancyListItemUi(
            id = "3",
            logoUrl = null,
            vacancyName = "Курьер",
            city = "Москва",
            employer = "Яндекс-Еда",
            salary = formatSalary(2_000, null, "USD"),
        ),
        VacancyListItemUi(
            id = "4",
            logoUrl = null,
            vacancyName = "Дантист",
            city = "Пермь",
            employer = "Ультра-Дент",
            salary = formatSalary(null, null, null),
        ),
    )

    VacancySearchAppTheme {
        VacanciesList(
            vacancies = sampleVacancies,
            onVacancyClick = {},
        )
    }
}
