package ru.practicum.android.diploma.domain.country

import kotlinx.coroutines.flow.Flow

interface CountryInteractor {
    fun searCountry(): Flow<Pair<List<Country>?, Int?>>
}
