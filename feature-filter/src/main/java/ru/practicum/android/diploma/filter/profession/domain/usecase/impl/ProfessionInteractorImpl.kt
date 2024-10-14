package ru.practicum.android.diploma.filter.profession.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.filter.profession.domain.model.Industry
import ru.practicum.android.diploma.filter.profession.domain.repository.ProfessionRepository
import ru.practicum.android.diploma.filter.profession.domain.usecase.ProfessionInteractor

class ProfessionInteractorImpl(val repository: ProfessionRepository) : ProfessionInteractor {
    override fun getIndustriesList(): Flow<Pair<List<Industry>?, String?>> {
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

