package ru.practicum.android.diploma.vacancy.ui.mock

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.vacancy.ui.VacancyState
import ru.practicum.android.diploma.vacancy.ui.VacancyViewModel

class VacancyDetailsPreviewProvider : PreviewParameterProvider<VacancyViewModel> {
    override val values = sequenceOf(
        VacancyViewModelMock(
            VacancyState.Content(
                details = VacancyDetailsPreview.full,
                isFavorite = false,
            )
        ),
        VacancyViewModelMock(
            VacancyState.Content(
                details = VacancyDetailsPreview.full,
                isFavorite = true,
            )
        ),
        VacancyViewModelMock(
            VacancyState.Content(
                details = VacancyDetailsPreview.withLogo,
                isFavorite = true,
                fromCache = true,
            )
        ),
        VacancyViewModelMock(
            VacancyState.Content(
                details = VacancyDetailsPreview.minimal,
                isFavorite = false,
            )
        ),
        VacancyViewModelMock(VacancyState.Loading),
        VacancyViewModelMock(VacancyState.NoInternet),
        VacancyViewModelMock(VacancyState.NotFound),
        VacancyViewModelMock(VacancyState.Error),
    )
}
