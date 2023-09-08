package ru.practicum.android.diploma.filter.data

import ru.practicum.android.diploma.filter.data.converter.countryDtoToCountry
import ru.practicum.android.diploma.filter.data.local_storage.LocalStorage
import ru.practicum.android.diploma.filter.data.model.DataType
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.ui.models.SelectedFilter
import ru.practicum.android.diploma.search.data.network.AlternativeRemoteDataSource
import ru.practicum.android.diploma.util.functional.Either
import ru.practicum.android.diploma.util.functional.Failure
import ru.practicum.android.diploma.util.functional.flatMap
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(
    private val sharedPrefsStorage: LocalStorage,
    private val apiHelper: AlternativeRemoteDataSource,
) : FilterRepository {

    override suspend fun saveSavedFilterSettings(key: String, selectedFilter: SelectedFilter) {
        sharedPrefsStorage.writeData(key = key, data = selectedFilter)
    }

    override suspend fun getSaveFilterSettings(key: String): SelectedFilter {
        return sharedPrefsStorage.readData(key = key, defaultValue = DataType.OBJECT)
    }

    override suspend fun getAllCountries(): Either<Failure, List<Country>> {
        return apiHelper.getAllCountries().flatMap {
            val list = countryDtoToCountry(it)
            Either.Right(list)
        }
    }

}