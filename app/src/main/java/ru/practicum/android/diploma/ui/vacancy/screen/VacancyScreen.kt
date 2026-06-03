package ru.practicum.android.diploma.ui.vacancy.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.vacancy.mapper.toContentUi
import ru.practicum.android.diploma.presentation.vacancy.state.VacancyDetailsUiState
import ru.practicum.android.diploma.ui.common.IconResource
import ru.practicum.android.diploma.ui.common.TopBar
import ru.practicum.android.diploma.ui.vacancy.components.VacancyDetailsContent
import ru.practicum.android.diploma.ui.vacancy.components.VacancyDetailsError
import ru.practicum.android.diploma.ui.vacancy.components.VacancyDetailsLoading
import ru.practicum.android.diploma.util.extentions.SalaryFormatLabels

@Composable
fun VacancyScreen(
    modifier: Modifier = Modifier,
    state: VacancyDetailsUiState,
    onLoadVacancy: () -> Unit,
    onBackClick: () -> Unit,
    onShareClick: (String) -> Unit,
    onFavoriteClick: () -> Unit,
    onEmailClick: (String) -> Unit,
    onPhoneClick: (String) -> Unit,
) {
    LaunchedEffect(Unit) {
        onLoadVacancy()
    }

    val isContent = state is VacancyDetailsUiState.Content
    val isFavorite = if (isContent) state.details.isFavorite else false

    Scaffold(
        modifier = modifier,
        topBar = {
            VacancyDetailsTopBar(
                showActions = isContent,
                onBackClick = onBackClick,
                onShareClick = {
                    if (state is VacancyDetailsUiState.Content) {
                        onShareClick(state.details.url)
                    }
                },
                onFavoriteClick = onFavoriteClick,
                isFavorite
            )
        },
    ) { paddingValues ->
        VacancyDetailsBody(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = state,
            onEmailClick = onEmailClick,
            onPhoneClick = onPhoneClick,
        )
    }
}

@Composable
private fun VacancyDetailsTopBar(
    showActions: Boolean,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    isFavorite: Boolean
) {
    TopBar(
        text = stringResource(R.string.vacancy_screen_title),
        navIconVisible = true,
        onNavClick = onBackClick,
        endFirstIconVisible = showActions,
        endFirstIcon = IconResource.DefaultIcon(
            resId = R.drawable.ic_share,
            contentDescriptionStringId = R.string.share,
            onClick = onShareClick,
            color = MaterialTheme.colorScheme.onBackground
        ),
        endSecondIconVisible = showActions,
        endSecondIcon = IconResource.LikeIcon(
            isActive = isFavorite,
            onClick = onFavoriteClick,
            defaultColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

@Composable
private fun VacancyDetailsBody(
    modifier: Modifier = Modifier,
    state: VacancyDetailsUiState,
    onEmailClick: (String) -> Unit,
    onPhoneClick: (String) -> Unit,
) {
    when (state) {
        VacancyDetailsUiState.Loading -> VacancyDetailsLoading(modifier = modifier)
        VacancyDetailsUiState.Error -> VacancyDetailsError(
            modifier = modifier,
            imageRes = R.drawable.img_no_internet,
            messageRes = R.string.no_internet_error,
        )

        VacancyDetailsUiState.NotFound -> VacancyDetailsError(
            modifier = modifier,
            imageRes = R.drawable.img_nothing_found,
            messageRes = R.string.vacancy_not_found_error,
        )

        VacancyDetailsUiState.ServerError -> VacancyDetailsError(
            modifier = modifier,
            imageRes = R.drawable.img_no_internet,
            messageRes = R.string.vacancy_server_error,
        )

        is VacancyDetailsUiState.Content -> {
            val contentUi = state.details.toContentUi(salaryFormatLabels())
            VacancyDetailsContent(
                modifier = modifier,
                content = contentUi,
                onEmailClick = onEmailClick,
                onPhoneClick = onPhoneClick,
            )
        }
    }
}

@Composable
private fun salaryFormatLabels(): SalaryFormatLabels {
    return SalaryFormatLabels(
        fromLabel = stringResource(R.string.from),
        toLabel = stringResource(R.string.to),
        emptyText = stringResource(R.string.salary_level_not_specified),
    )
}
