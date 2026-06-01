package ru.practicum.android.diploma.area.ui.mock

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.area.ui.AreaScreenState
import ru.practicum.android.diploma.area.ui.AreaViewModel
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.core.ui.preview.mock1
import ru.practicum.android.diploma.core.ui.preview.mock2

class AreaPreviewProvider : PreviewParameterProvider<AreaViewModel> {
    override val values = sequenceOf(
        AreaViewModelMock(AreaScreenState()),
        AreaViewModelMock(AreaScreenState(country = Area.mock1())),
        AreaViewModelMock(AreaScreenState(region = Area.mock2())),
        AreaViewModelMock(AreaScreenState(country = Area.mock1(), region = Area.mock2())),
    )
}
