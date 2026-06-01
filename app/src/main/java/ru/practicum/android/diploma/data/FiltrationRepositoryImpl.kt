package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.storage.FiltrationStorage
import ru.practicum.android.diploma.domain.api.FiltrationRepository
import ru.practicum.android.diploma.domain.models.FilterParameters

class FiltrationRepositoryImpl(
    private val filtrationStorage: FiltrationStorage,
) : FiltrationRepository {

    override fun saveFilter(filterParameters: FilterParameters) {
        filtrationStorage.saveFilter(filterParameters)
    }

    override fun getFilter(): FilterParameters {
        return filtrationStorage.getFilter()
    }

    override fun clearFilter() {
        filtrationStorage.clearFilter()
    }
}
