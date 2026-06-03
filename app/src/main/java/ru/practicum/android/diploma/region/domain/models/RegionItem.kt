package ru.practicum.android.diploma.region.domain.models

import ru.practicum.android.diploma.core.domain.models.Area

data class RegionItem(
    val region: Area,
    val country: Area
)
