package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.ui.models.SelectedFilter
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure

interface FilterRepository {

    suspend fun saveSavedFilterSettings(key: String, selectedFilter: SelectedFilter)
    suspend fun getSaveFilterSettings(key: String): SelectedFilter

    suspend fun getAllCountries() : Either<Failure, List<Country>>
    suspend fun getIndustries() : Either<Failure, List<Industry>>




}