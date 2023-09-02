package ru.practicum.android.diploma.db.data.converter

import android.annotation.SuppressLint
import ru.practicum.android.diploma.db.data.entity.VacancyEntity
import ru.practicum.android.diploma.domain.models.Vacancy
import java.text.SimpleDateFormat
import java.util.Date

class VacancyDbConverter {
    fun map(vacancyEntity: VacancyEntity): Vacancy{
        return Vacancy(
            id = vacancyEntity.id,
            name = vacancyEntity.name,
            city = vacancyEntity.city,
            employerName = vacancyEntity.employerName,
            employerLogoUrl = vacancyEntity.employerLogoUrl,
            salaryCurrency = vacancyEntity.salaryCurrency,
            salaryFrom = vacancyEntity.salaryFrom,
            salaryTo = vacancyEntity.salaryTo
        )
    }

    fun map(vacancy: Vacancy): VacancyEntity{
        return VacancyEntity(
            id = vacancy.id,
            name = vacancy.name,
            city = vacancy.city,
            employerName = vacancy.employerName,
            employerLogoUrl = null,
            salaryCurrency = vacancy.salaryCurrency,
            salaryFrom = vacancy.salaryFrom,
            salaryTo = vacancy.salaryTo,
            getCurrentDate()
        )
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        return sdf.format(Date())
    }
}