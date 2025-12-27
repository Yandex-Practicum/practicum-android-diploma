package ru.practicum.android.diploma.core.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.core.presentation.ui.theme.VacancySearchAppTheme

data class VacancyListItemUi(
    val id: String,
    val logoUrl: String?,
    val vacancyName: String,
    val city: String?,
    val employer: String?,
    val salary: String?,
)

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
                modifier = Modifier
                    .fillMaxWidth(),
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
            salary = "от 150 000 ₽",
        ),
        VacancyListItemUi(
            id = "2",
            logoUrl = null,
            vacancyName = "Android-разработчик",
            city = "Санкт-Петербург",
            employer = "Яндекс",
            salary = "до 250 000 ₽",
        ),
        VacancyListItemUi(
            id = "3",
            logoUrl = null,
            vacancyName = "Курьер",
            city = "Москва",
            employer = "Яндекс-Еда",
            salary = "",
        ),
    )

    VacancySearchAppTheme {
        VacanciesList(
            vacancies = sampleVacancies,
            onVacancyClick = {},
        )
    }
}
