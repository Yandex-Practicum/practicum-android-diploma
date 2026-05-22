package ru.practicum.android.diploma.ui.common.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.common.BadgeItem
import ru.practicum.android.diploma.ui.mocks.MocData
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun VacanciesContent(
    modifier: Modifier = Modifier,
    vacancies: List<Vacancy>,
    vacancyAmount: Int,
    onVacancyClick: () -> Unit,
) {
    Column(modifier = modifier) {
        if (vacancies.isNotEmpty()) {
            Box(contentAlignment = Alignment.TopCenter) {
                VacancyList(
                    vacancies = vacancies,
                    onClick = onVacancyClick,
                )
                BadgeItem(
                    modifier = Modifier
                        .padding(top = 4.dp, bottom = 8.dp)
                        .zIndex(1f),
                    vacancyAmount = vacancyAmount
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun VacanciesContentPreview() {
    AppTheme {
        VacanciesContent(
            vacancies = MocData.vacancies,
            vacancyAmount = MocData.VACANCY_AMOUNT,
            onVacancyClick = {},
        )
    }
}
