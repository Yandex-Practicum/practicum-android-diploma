package ru.practicum.android.diploma.industry.ui.mock

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.core.domain.models.Industry
import ru.practicum.android.diploma.core.ui.preview.mock2
import ru.practicum.android.diploma.industry.ui.IndustryError
import ru.practicum.android.diploma.industry.ui.IndustryScreenState
import ru.practicum.android.diploma.industry.ui.IndustryViewModel

class IndustryPreviewProvider : PreviewParameterProvider<IndustryViewModel> {
    override val values = sequenceOf(
        IndustryViewModelMock("",IndustryScreenState.Default),
        IndustryViewModelMock("",IndustryScreenState.Error(IndustryError.NOT_FOUND)),
        IndustryViewModelMock("",IndustryScreenState.Content(
            industries = Industry.mock2()
        )),
        IndustryViewModelMock("Авиационная, вертолетная и ",IndustryScreenState.Content(
            industries = Industry.mock2()
        ))
    )
}
