package ru.practicum.android.diploma.ui.common.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.mocks.MocData

@Composable
fun VacancyList(
    modifier: Modifier = Modifier,
    vacancies: List<Vacancy>,
    onClick: () -> Unit = {},
) {
    LazyColumn(
        modifier = modifier,
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

@Preview
@Composable
private fun VacancyListPreview() {
    VacancyList(
        vacancies = MocData.vacancies,
    )
}
