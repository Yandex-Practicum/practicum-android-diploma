package ru.practicum.android.diploma.ui.common.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
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
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy

@Composable
fun VacancyList(
    modifier: Modifier = Modifier,
    vacancies: List<Vacancy>,
    onClick: () -> Unit = {},
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
    }
}

private val mockVacancies = listOf(
    Vacancy(
        id = "1",
        name = "Senior Android Developer",
        company = "TechCorp",
        city = "Москва",
        salary = Salary(from = 300_000, to = 450_000, currency = "RUB"),
        logo = null
    ),
    Vacancy(
        id = "2",
        name = "Junior Kotlin Developer",
        company = "StartupO",
        city = "Санкт-Петербург",
        salary = Salary(from = 70_000, to = 100_000, currency = "RUB"),
        logo = null
    ),
    Vacancy(
        id = "3",
        name = "Middle Android Engineer",
        company = "GlobalFinance",
        city = null,
        salary = Salary(from = 3_000, to = 4_500, currency = "USD"),
        logo = null
    ),
    Vacancy(
        id = "4",
        name = "QA Automation (Kotlin)",
        company = "MegaRetail",
        city = "Казань",
        salary = null,
        logo = null
    ),
    Vacancy(
        id = "5",
        name = "Team Lead Android",
        company = null,
        city = "Новосибирск",
        salary = Salary(from = 500_000, to = null, currency = "RUB"),
        logo = null
    )
)

@Preview
@Composable
private fun VacancyListPreview() {
    VacancyList(
        vacancies = mockVacancies,
        onLoadNextPage = {}
    )
}
