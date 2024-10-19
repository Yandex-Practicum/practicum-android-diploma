package ru.practicum.android.diploma.filters.areas.domain.api

import ru.practicum.android.diploma.filters.areas.domain.models.Area

interface AreaCashRepository {
    fun setCashArea(area: Area)
    fun cleanArea()
    fun cleanCashRegion()
    fun cleanCashArea()
    fun saveArea()
    fun getCashArea(): Area?
    fun resetCashArea()
}
