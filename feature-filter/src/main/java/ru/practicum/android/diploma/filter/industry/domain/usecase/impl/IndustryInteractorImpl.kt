package ru.practicum.android.diploma.filter.industry.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel
import ru.practicum.android.diploma.filter.industry.domain.repository.IndustryRepository
import ru.practicum.android.diploma.filter.industry.domain.usecase.IndustryInteractor

class IndustryInteractorImpl(val repository: IndustryRepository) : IndustryInteractor {
    override fun getIndustriesList(): Flow<Pair<List<IndustryModel>?, String?>> {
        return repository.getIndustriesList().map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, "")
                }

                is Resource.Error -> {
                    Pair(null, result.message ?: "")
                }
            }

        }
    }
}
