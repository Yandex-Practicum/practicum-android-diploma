package ru.practicum.android.diploma.filter.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.IndustryViewState

interface FilterInteractor {
    fun getIndustries(): Flow<IndustryViewState>
}
