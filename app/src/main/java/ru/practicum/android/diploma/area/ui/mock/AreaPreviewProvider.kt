package ru.practicum.android.diploma.area.ui.mock

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import ru.practicum.android.diploma.area.ui.AreaScreenState
import ru.practicum.android.diploma.area.ui.AreaViewModel

class AreaPreviewProvider : PreviewParameterProvider<AreaViewModel> {
    var loremIpsum = LoremIpsum(1).values.first()
    override val values = sequenceOf(
        AreaViewModelMock(AreaScreenState()),
        AreaViewModelMock(AreaScreenState(country = loremIpsum)),
        AreaViewModelMock(AreaScreenState(region = loremIpsum)),
        AreaViewModelMock(AreaScreenState(country = loremIpsum, region = loremIpsum)),
    )
}
