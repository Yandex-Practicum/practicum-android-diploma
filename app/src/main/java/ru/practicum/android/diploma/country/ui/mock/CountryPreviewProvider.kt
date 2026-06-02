package ru.practicum.android.diploma.country.ui.mock

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.core.ui.preview.mock1
import ru.practicum.android.diploma.country.ui.CountryError
import ru.practicum.android.diploma.country.ui.CountryScreenState
import ru.practicum.android.diploma.country.ui.CountryViewModel

class CountryPreviewProvider : PreviewParameterProvider<CountryViewModel> {
    var loremIpsum = LoremIpsum(1).values.first()
    override val values = sequenceOf(
        CountryViewModelMock(CountryScreenState.Loading),
        CountryViewModelMock(CountryScreenState.Content(listOf(Area.mock1()))),
        CountryViewModelMock(CountryScreenState.Error(CountryError.NO_INTERNET)),
        CountryViewModelMock(CountryScreenState.Error(CountryError.ERROR))
    )
}
