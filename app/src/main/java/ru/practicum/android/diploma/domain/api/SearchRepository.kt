package ru.practicum.android.diploma.domain.api

import com.bumptech.glide.load.engine.Resource
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface SearchRepository {
    fun search(expression: String): Flow<ru.practicum.android.diploma.Resource<List<Vacancy>>>

}
