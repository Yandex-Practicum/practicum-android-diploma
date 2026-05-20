package ru.practicum.android.diploma.ui.search.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.common.BadgeItem
import ru.practicum.android.diploma.ui.common.VacancyItem
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun JobSearchScreen(modifier: Modifier = Modifier, vacancies: List<Vacancy>, onClick: () -> Unit = {}) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BadgeItem(modifier = Modifier.padding(top = 4.dp, bottom = 8.dp), vacancyAmount = 295)
        LazyColumn {
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

@Preview(showSystemUi = true)
@Composable
private fun JobSearchScreenPreview() {
    AppTheme {
        JobSearchScreen(modifier = Modifier.fillMaxSize(), vacancies = mockVacancies)
    }
}
