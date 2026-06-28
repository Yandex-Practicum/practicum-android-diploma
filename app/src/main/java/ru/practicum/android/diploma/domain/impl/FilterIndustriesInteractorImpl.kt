package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.FilterIndustriesInteractor
import ru.practicum.android.diploma.domain.api.FilterIndustriesRepository
import ru.practicum.android.diploma.domain.models.Industry

class FilterIndustriesInteractorImpl(private val repository: FilterIndustriesRepository) :
    FilterIndustriesInteractor {
    override fun getIndustries(query: String): Flow<List<Industry>> {
       return repository.getIndustries(query)
    }
}
