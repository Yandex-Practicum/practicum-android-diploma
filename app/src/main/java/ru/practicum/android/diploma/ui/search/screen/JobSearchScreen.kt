package ru.practicum.android.diploma.ui.search.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.search.state.JobSearchState
import ru.practicum.android.diploma.ui.common.Placeholder
import ru.practicum.android.diploma.ui.common.search.VacanciesContent
import ru.practicum.android.diploma.ui.mocks.MocData
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun JobSearchScreen(
    modifier: Modifier = Modifier,
    state: JobSearchState,
    onVacancyClick: () -> Unit,
    onLoadNextPage: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.weight(1F)) {
            when (state) {
                is JobSearchState.Content -> VacanciesContent(
                    modifier = Modifier.fillMaxSize(),
                    vacancies = state.vacancies,
                    vacancyAmount = state.found,
                    onVacancyClick = onVacancyClick,
                    onLoadNextPage = onLoadNextPage,
                    isLoading = state.isLoading
                )

                JobSearchState.Initial -> Placeholder(
                    modifier = Modifier.fillMaxSize(),
                    imageRes = R.drawable.ic_initial_placeholder_328
                )
            }
        }
    }
}

private val jobSearchState = JobSearchState.Content(
    found = MocData.VACANCY_AMOUNT,
    vacancies = MocData.vacancies,
    isLoading = true
)

@Preview(showSystemUi = true)
@Composable
private fun JobSearchScreenPreview() {
    AppTheme {
        JobSearchScreen(
            modifier = Modifier.fillMaxSize(),
            state = jobSearchState,
            onVacancyClick = { },
            onLoadNextPage = {},
        )
    }
}
