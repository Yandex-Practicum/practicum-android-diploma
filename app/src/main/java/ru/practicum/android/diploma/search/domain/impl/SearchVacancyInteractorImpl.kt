package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.search.domain.api.SearchVacancyInteractor
import ru.practicum.android.diploma.search.domain.api.SearchVacancyRepository
import ru.practicum.android.diploma.search.domain.models.VacancySearch
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.network.HttpStatusCode

class SearchVacancyInteractorImpl(private val repository: SearchVacancyRepository) : SearchVacancyInteractor {

    override fun getVacancyList(
        query: HashMap<String, String>
    ): Flow<Pair<List<VacancySearch>?, HttpStatusCode?>> {
        return repository.getVacancyList(query).map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, result.httpStatusCode)
                is Resource.Error -> Pair(null, result.httpStatusCode)
            }
        }
    }

}
