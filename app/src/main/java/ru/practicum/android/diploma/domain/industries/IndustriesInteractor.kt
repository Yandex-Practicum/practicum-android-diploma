package ru.practicum.android.diploma.domain.industries

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.model.industries.IndustriesFullDto
import ru.practicum.android.diploma.ui.search.state.VacancyError

interface IndustriesInteractor {
    fun getIndustries(): Flow<Pair<List<IndustriesFullDto>?, VacancyError?>>
}
