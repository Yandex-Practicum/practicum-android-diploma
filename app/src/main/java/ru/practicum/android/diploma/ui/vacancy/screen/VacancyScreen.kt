package ru.practicum.android.diploma.ui.vacancy.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.vacancy.mapper.toContentUi
import ru.practicum.android.diploma.presentation.vacancy.model.VacancyDetailContentUi
import ru.practicum.android.diploma.presentation.vacancy.state.VacancyDetailsUiState
import ru.practicum.android.diploma.util.extentions.SalaryFormatLabels

@Composable
fun VacancyScreen(
    modifier: Modifier = Modifier,
    state: VacancyDetailsUiState,
    onLoadVacancy: () -> Unit,
    @Suppress("UnusedParameter") navigateBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        onLoadVacancy()
    }

    when (state) {
        VacancyDetailsUiState.Loading -> VacancyLoadingContent(modifier)
        VacancyDetailsUiState.Error,
        VacancyDetailsUiState.NotFound,
        VacancyDetailsUiState.ServerError,
        -> VacancyErrorContent(
            message = stringResource(R.string.no_vacancies_error),
            modifier = modifier,
        )
        is VacancyDetailsUiState.Content -> {
            val salaryLabels = SalaryFormatLabels(
                fromLabel = stringResource(R.string.from),
                toLabel = stringResource(R.string.to),
                emptyText = stringResource(R.string.salary_level_not_specified),
            )
            val contentUi = remember(state.details, salaryLabels) {
                state.details.toContentUi(salaryLabels)
            }
            VacancyDetailContent(
                contentUi = contentUi,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun VacancyDetailContent(
    contentUi: VacancyDetailContentUi,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        Text(
            text = contentUi.title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = contentUi.salaryText,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        if (contentUi.descriptionText.isNotBlank()) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = contentUi.descriptionText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Composable
private fun VacancyLoadingContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(modifier = Modifier.padding(top = 48.dp))
    }
}

@Composable
private fun VacancyErrorContent(
    message: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}
