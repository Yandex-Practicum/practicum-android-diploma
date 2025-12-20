package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.search.data.dto.FilterAreaDto
import ru.practicum.android.diploma.search.data.network.Resource
import ru.practicum.android.diploma.search.domain.api.VacancyInteractor
import ru.practicum.android.diploma.search.domain.api.VacancyRepository

class VacancyInteractorImpl(private val repository: VacancyRepository): VacancyInteractor {
    override fun getAreas(): Flow<Pair<List<FilterAreaDto>?, String?>> {
        return repository.getAreas().map { result ->
            when(result) {
                is Resource.Success<*> -> {
                    Pair(result.data, null)
                }

                is Resource.Error<*> -> {
                    Pair(null, result.message)
                }
            }
        }
    }

}
