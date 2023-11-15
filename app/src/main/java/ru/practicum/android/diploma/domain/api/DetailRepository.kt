package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.detail.FullVacancy
import ru.practicum.android.diploma.util.Resource

interface DetailRepository {
    fun getVacancy(id: String): Flow<Resource<FullVacancy>>
}