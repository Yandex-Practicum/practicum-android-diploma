package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.AreaResult

interface AreaInteractor {
    suspend fun getAreas(): AreaResult
}
