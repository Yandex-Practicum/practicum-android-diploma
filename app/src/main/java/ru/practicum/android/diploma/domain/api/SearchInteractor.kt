package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface SearchInteractor {
    fun loadVacancies(query: String): Flow<Pair<List<Vacancy>?, String?>>
    fun getVacancies(options: HashMap<String,String>): Flow<Pair<List<Vacancy>?, String?>>
}