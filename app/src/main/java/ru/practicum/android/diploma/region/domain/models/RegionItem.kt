package ru.practicum.android.diploma.region.domain.models

import ru.practicum.android.diploma.core.domain.models.Area

// Транспортная модель: регион и страна, которой он принадлежит.
// Страна нужна для авто-подстановки на AreaScreen без доп. запроса.
data class RegionItem(
    val region: Area,
    val country: Area
)
