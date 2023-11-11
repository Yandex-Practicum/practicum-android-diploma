package ru.practicum.android.diploma.domain.models.filter

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.util.DataResponse

interface FilterInteractor {
    fun setSalary(input: String)

    fun getSalary(): String
    fun getAreas(areaId: String): Flow<DataResponse<Area>>

    fun getCountries(): Flow<Pair<List<Country>?, String?>>
    fun setSelectedCountry(country: Country?)
    fun getSelectedCountry(): Country?

    fun getSelectedCountryLiveData(): LiveData<Country?>

}
