package ru.practicum.android.diploma.domain.country

import kotlinx.coroutines.flow.Flow

interface CountryInteractor {
    fun searchCountry(): Flow<Pair<List<Country>?, Int?>>
}
