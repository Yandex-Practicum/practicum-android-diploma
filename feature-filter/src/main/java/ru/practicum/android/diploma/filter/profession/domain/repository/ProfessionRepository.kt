package ru.practicum.android.diploma.filter.profession.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.filter.profession.domain.model.IndustryModel

interface ProfessionRepository {
    fun getIndustriesList(): Flow<Resource<List<IndustryModel>>>
}
