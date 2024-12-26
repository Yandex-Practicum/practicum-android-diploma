package ru.practicum.android.diploma.domain.api.region

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.ui.search.state.VacancyError

interface RegionsInteractor {

    fun getRegions(areaId: String? = null): Flow<Pair<List<Region>?, VacancyError?>>
}
