package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.IndustryResult

interface IndustryRepository {
    suspend fun getIndustries(): IndustryResult
}
