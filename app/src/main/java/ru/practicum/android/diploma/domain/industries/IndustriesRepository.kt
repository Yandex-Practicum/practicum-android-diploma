package ru.practicum.android.diploma.domain.industries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.response.Industries
import ru.practicum.android.diploma.util.Resource

interface IndustriesRepository {

    fun searchIndustries(industries: String): Flow<Resource<List<Industries>>>
}
