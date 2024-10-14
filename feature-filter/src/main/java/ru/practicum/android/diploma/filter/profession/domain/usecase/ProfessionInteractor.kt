package ru.practicum.android.diploma.filter.profession.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.profession.domain.model.Industry

interface ProfessionInteractor {
    fun getIndustriesList(): Flow<Pair<List<Industry>?, String?>>
}
