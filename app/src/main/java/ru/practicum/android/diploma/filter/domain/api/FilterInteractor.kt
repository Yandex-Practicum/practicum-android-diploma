package ru.practicum.android.diploma.filter.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.data.network.dto.CountryDto


interface FilterInteractor {

    fun filter()

    suspend fun getCountries(): Flow<CountryDto>
}