package ru.practicum.android.diploma.core.ui.preview

import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.core.domain.models.Industry

fun Area.Companion.mock1(): Area {
    return Area(
        id = "1",
        name = "Москва",
    )
}
