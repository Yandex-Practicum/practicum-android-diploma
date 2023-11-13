package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.similar.SimilarInteractor
import ru.practicum.android.diploma.domain.similar.SimilarRepository
import ru.practicum.android.diploma.util.Resource

class SimilarInteractorImpl(private val repository: SimilarRepository) : SimilarInteractor {
    override fun loadVacancies(vacancyId: String): Flow<Pair<List<Vacancy>?, String?>> {
        return repository.searchVacancies(vacancyId).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }

}