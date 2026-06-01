package ru.practicum.android.diploma.area.ui

import ru.practicum.android.diploma.core.domain.models.Area

data class AreaScreenState(
    val country: Area? = null,
    val region: Area? = null
)
