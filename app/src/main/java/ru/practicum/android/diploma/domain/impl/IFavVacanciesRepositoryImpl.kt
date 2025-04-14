package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.IFavVacanciesRepository

class IFavVacanciesRepositoryImpl(val dataBase: AppDataBase): IFavVacanciesRepository {
    override fun add(vacancy: Vacancy) {
        dataBase.insertVacancy(convertFromVacancy(vacancy))
    }

    override fun delete(vacancy: Vacancy) {
        dataBase.deleteVacancy(convertFromVacancy(vacancy))
    }

    override fun getAll(): Flow<List<Vacancy>> {
       return  convertToVacancy(dataBase.getAllVacancies())
    }

    private fun convertToVacancy(vacancy: FavVacancyEntity): Vacancy{
        return Vacancy(
            vacancy.id,
            vacancy.name,
            vacancy.areaId,
            vacancy.areaName,
            vacancy.description,
            vacancy.employerId,
            vacancy.employerName,
            vacancy.employerLogoUrl,
            vacancy.salaryFrom,
            vacancy.salaryGross,
            vacancy.salaryTo,
            vacancy.salaryCurrency,
            vacancy.experienceId,
            vacancy.experienceName
        )
    }
    private fun convertFromVacancy(vacancy: Vacancy): FavVacancyEntity{
        return FavVacancyEntity(
            vacancy.id,
            vacancy.name,
            vacancy.areaId,
            vacancy.areaName,
            vacancy.description,
            vacancy.employerId,
            vacancy.employerName,
            vacancy.employerLogoUrl,
            vacancy.salaryFrom,
            vacancy.salaryGross,
            vacancy.salaryTo,
            vacancy.salaryCurrency,
            vacancy.experienceId,
            vacancy.experienceName
        )
    }
}
