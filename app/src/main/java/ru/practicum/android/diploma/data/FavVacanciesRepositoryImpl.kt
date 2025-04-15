package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.FavVacancyEntity
import ru.practicum.android.diploma.data.dto.Area
import ru.practicum.android.diploma.data.dto.Employer
import ru.practicum.android.diploma.data.dto.Experience
import ru.practicum.android.diploma.data.dto.KeySkill
import ru.practicum.android.diploma.data.dto.LogoUrls
import ru.practicum.android.diploma.data.dto.Salary
import ru.practicum.android.diploma.data.dto.Vacancy
import ru.practicum.android.diploma.data.dto.VacancyDetails
import ru.practicum.android.diploma.data.mapper.VacancyEntityMapper
import ru.practicum.android.diploma.domain.api.IFavVacanciesRepository

class FavVacanciesRepositoryImpl(val dataBase: AppDatabase, val mapper: VacancyEntityMapper) : IFavVacanciesRepository {
    override suspend fun add(vacancy: VacancyDetails) {
        dataBase.favVacanciesDao().insertVacancy(mapper.convertFromVacancyDetails(vacancy))
    }

    override suspend fun delete(vacancy: VacancyDetails) {
        dataBase.favVacanciesDao().deleteVacancy(mapper.convertFromVacancyDetails(vacancy))
    }

    override fun getAll(): Flow<List<VacancyDetails>> = flow {
        val vacancies = dataBase.favVacanciesDao().getAllVacancies()
        emit(vacancies.map { mapper.convertToVacancyDetails(it) })
    }
}
