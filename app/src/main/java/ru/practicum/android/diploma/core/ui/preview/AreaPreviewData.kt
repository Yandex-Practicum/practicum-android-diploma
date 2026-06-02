package ru.practicum.android.diploma.core.ui.preview

import ru.practicum.android.diploma.core.domain.models.Area

fun Area.Companion.mock1(): Area {
    return Area(
        id = "1",
        name = "Москва",
    )
}

fun Area.Companion.mock2(): Area {
    return Area(
        id = "1",
        name = "Ростовская область",
    )
}
