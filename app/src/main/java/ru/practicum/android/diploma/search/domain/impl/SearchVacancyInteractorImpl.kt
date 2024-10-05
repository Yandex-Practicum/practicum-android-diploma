package ru.practicum.android.diploma.search.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.search.domain.api.SearchVacancyInteractor
import ru.practicum.android.diploma.search.domain.api.SearchVacancyRepository
import ru.practicum.android.diploma.search.domain.models.VacancySearch
import ru.practicum.android.diploma.util.Resource

class SearchVacancyInteractorImpl(private val repository: SearchVacancyRepository) : SearchVacancyInteractor {

    override fun getVacancyList(
        query: HashMap<String, String>
    ): Flow<List<VacancySearch>?> {
        return repository.getVacancyList(query).map { result ->
            when (result) {
                // передаем данные в случае успешного запроса, либо пустой лист, если ничего не нашлось
                is Resource.Success -> result.data

                //  в случае ошибки отдаем null
                is Resource.Error -> null
            }
        }
    }

}
