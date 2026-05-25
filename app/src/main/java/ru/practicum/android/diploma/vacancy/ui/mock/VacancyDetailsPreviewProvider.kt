package ru.practicum.android.diploma.vacancy.ui.mock

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.vacancy.ui.VacancyDetailsViewState
import ru.practicum.android.diploma.vacancy.ui.VacancyViewModel

class VacancyDetailsPreviewProvider : PreviewParameterProvider<VacancyViewModel> {
    override val values = sequenceOf(
        VacancyViewModelMock(VacancyDetailsViewState.Data(VacancyDetailsPreview.full)),
        VacancyViewModelMock(VacancyDetailsViewState.Data(VacancyDetailsPreview.minimal)),
        VacancyViewModelMock(VacancyDetailsViewState.Data(VacancyDetailsPreview.withLogo))
    )
}
