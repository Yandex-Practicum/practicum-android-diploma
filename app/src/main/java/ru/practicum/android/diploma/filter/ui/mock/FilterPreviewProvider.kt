package ru.practicum.android.diploma.filter.ui.mock

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.core.domain.models.Filters
import ru.practicum.android.diploma.core.domain.models.Industry
import ru.practicum.android.diploma.core.ui.preview.mock1
import ru.practicum.android.diploma.filter.ui.FilterViewModel

class FilterPreviewProvider : PreviewParameterProvider<FilterViewModel> {
    override val values = sequenceOf(
        FilterViewModelMock(Filters()),
        FilterViewModelMock(Filters(industry = Industry.mock1())),
        FilterViewModelMock(Filters(industry = Industry.mock1(), area = Area.mock1())),
        FilterViewModelMock(Filters(industry = Industry.mock1(), area = Area.mock1(), salary = "500")),
        FilterViewModelMock(
            Filters(industry = Industry.mock1(), area = Area.mock1(), salary = "500", onlyWithSalary = true)
        )
    )
}
