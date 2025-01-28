package ru.practicum.android.diploma.common.util

import androidx.room.TypeConverter
import ru.practicum.android.diploma.common.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.VacancyItems
import java.time.Instant

class VacancyEntityConverter {
    @TypeConverter
    fun map(vacancy: VacancyItems): VacancyEntity {
        return VacancyEntity(
            vacancy.id, vacancy.name, vacancy.areaName, vacancy.employer,
            vacancy.iconUrl, vacancy.salary?.from, vacancy.salary?.to, vacancy.salary?.currency,
            Instant.now().epochSecond
        )
    }

    @TypeConverter
    fun map(vacancy: VacancyEntity): VacancyItems {
        var from = vacancy.salaryFrom
        var to = vacancy.salaryTo
        var currency = vacancy.salaryCurrency

        return if (from == null && to == null && currency.isNullOrBlank()) {
            VacancyItems(
                vacancy.id,
                vacancy.name,
                vacancy.areaName,
                vacancy.employer,
                vacancy.iconUrl,
                null
            )
        } else {
            VacancyItems(
                vacancy.id,
                vacancy.name,
                vacancy.areaName,
                vacancy.employer,
                vacancy.iconUrl,
                Salary(from, to, currency)
            )
        }
    }
}
