package ru.practicum.android.diploma.core.ui.utils

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.core.ui.preview.mockList
import ru.practicum.android.diploma.core.ui.theme.AppTheme

// Используется в поиске и в избранных
@Composable
fun VacancyList(
    vacancies: List<Vacancy>,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    isNextPageLoading: Boolean = false,
    onVacancyClick: (Vacancy) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(vacancies, key = { it.id }) { vacancy ->
            VacancyCard(vacancy = vacancy, onClick = { onVacancyClick(vacancy) })
        }
        if (isNextPageLoading) {
            item {
                SmallLoadingContent()
            }
        }
    }
}

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun VacancyListPreview() {
    AppTheme {
        VacancyList(Vacancy.mockList()) {}
    }
}
