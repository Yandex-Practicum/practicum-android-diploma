package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.AreaResult

interface AreaRepository {
    suspend fun getAreas(): AreaResult
}
