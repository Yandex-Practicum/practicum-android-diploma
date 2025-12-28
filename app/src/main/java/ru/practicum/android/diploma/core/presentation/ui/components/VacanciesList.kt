package ru.practicum.android.diploma.core.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.core.presentation.ui.model.VacancyListItemUi
import ru.practicum.android.diploma.core.presentation.ui.theme.VacancySearchAppTheme
import ru.practicum.android.diploma.core.presentation.ui.util.formatSalary

@Composable
fun VacanciesList(
    vacancies: List<VacancyListItemUi>,
    modifier: Modifier = Modifier,
    onVacancyClick: (String) -> Unit,
) {
    LazyColumn(
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
