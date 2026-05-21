package ru.practicum.android.diploma.core.ui.utils

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.core.domain.mocks.mockList
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.core.ui.theme.AppTheme

// Используется в поиске и в избранных
@Composable
fun VacancyList(items: List<Vacancy>, modifier: Modifier = Modifier, onClick: (Vacancy) -> Unit) {
    LazyColumn(modifier = modifier) {
        items(
            count = items.count(),
            key = { index -> items[index].id },
            contentType = { index -> Vacancy }
        ) { index ->
            VacancyCard(items[index], onClick)
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
