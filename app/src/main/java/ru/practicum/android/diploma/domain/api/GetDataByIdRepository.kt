package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.favourites.room.VacancyDetails
import ru.practicum.android.diploma.data.search.network.Resource

interface GetDataByIdRepository {
    fun getById(id: String): Flow<Resource<VacancyDetails>>
}
