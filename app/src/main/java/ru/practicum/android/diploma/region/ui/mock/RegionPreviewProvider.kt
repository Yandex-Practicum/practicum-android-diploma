package ru.practicum.android.diploma.region.ui.mock

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.region.domain.models.RegionItem
import ru.practicum.android.diploma.region.ui.RegionError
import ru.practicum.android.diploma.region.ui.RegionScreenState
import ru.practicum.android.diploma.region.ui.RegionViewModel

class RegionPreviewProvider : PreviewParameterProvider<RegionViewModel> {
    private val russia = Area("113", "Россия")
    private val sample = listOf(
        RegionItem(Area("1", "Москва"), russia),
        RegionItem(Area("2", "Санкт-Петербург"), russia),
        RegionItem(Area("3", "Ростовская область"), russia),
    )
    override val values = sequenceOf(
        RegionViewModelMock(RegionScreenState.Loading),
        RegionViewModelMock(RegionScreenState.Content(sample)),
        RegionViewModelMock(RegionScreenState.Empty),
        RegionViewModelMock(RegionScreenState.Error(RegionError.NO_INTERNET)),
    )
}
