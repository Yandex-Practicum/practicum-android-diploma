package ru.practicum.android.diploma.domain.interactors

import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

interface IndustriesInteractor {
    suspend fun getIndustries(): Resource<List<Industry>>
}
