package ru.practicum.android.diploma.ui.common.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.common.BadgeItem
import ru.practicum.android.diploma.ui.common.Loader
import ru.practicum.android.diploma.ui.mocks.MocData
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun VacanciesContent(
    modifier: Modifier = Modifier,
    vacancies: List<Vacancy>,
    vacancyAmount: Int,
    isLoading: Boolean,
    onVacancyClick: (String) -> Unit,
    onLoadNextPage: () -> Unit
) {
    Column(modifier = modifier) {
        Box(contentAlignment = Alignment.TopCenter) {
            VacancyList(
                vacancies = vacancies,
                isLoading = isLoading,
                onClick = onVacancyClick,
                onLoadNextPage = onLoadNextPage
            )
            if (vacancies.isNotEmpty()) {
                BadgeItem(
                    modifier = Modifier
                        .padding(top = 12.dp, bottom = 8.dp)
                        .zIndex(1f),
                    vacancyAmount = vacancyAmount
                )
            }
        }
        if (isLoading && vacancies.isEmpty()) {
            Loader(
                modifier
                    .weight(1F)
                    .imePadding()
            )
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
            isLoading = true,
            onVacancyClick = {},
            onLoadNextPage = {}
        )
    }
}
