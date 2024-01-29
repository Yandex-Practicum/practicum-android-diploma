package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.ErrorNetwork
import ru.practicum.android.diploma.domain.models.Vacancy

interface SearchInteractor {
    fun search(expression: String): Flow<Pair<List<Vacancy>?, ErrorNetwork?>>


}
