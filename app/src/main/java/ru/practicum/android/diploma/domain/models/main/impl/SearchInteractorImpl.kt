package ru.practicum.android.diploma.domain.models.main.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchRequest
import ru.practicum.android.diploma.domain.api.Resource
import ru.practicum.android.diploma.domain.api.SearchRepository
import ru.practicum.android.diploma.domain.models.main.SearchInteractor
import ru.practicum.android.diploma.domain.models.main.Vacancy

class SearchInteractorImpl (
    val repository: SearchRepository
): SearchInteractor{
    override fun searchTrack(term: String): Flow<List<Vacancy>> = flow{
//        return repository.makeRequest(VacanciesSearchRequest(term)).map { result ->
//            when(result) {
//                is Resource.Success -> {
//                    result.data
//                }
//                is Resource.Error -> {
//                    result.message
//                }
//            }
//        }
        //TODO: Доделать реализацию интерактора
    }
}
