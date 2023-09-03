package ru.practicum.android.diploma.search.data.network.converter

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.data.network.dto.VacancyDto
import ru.practicum.android.diploma.search.data.network.dto.general_models.Salary
import ru.practicum.android.diploma.search.domain.models.Vacancy
import javax.inject.Inject

class VacancyModelConverter @Inject constructor(
    private val context: Context,
) {
    
    fun mapList(list: List<VacancyDto>): List<Vacancy> {
        return list.map { it.map() }
    }
    
    private fun VacancyDto.map(): Vacancy {
        return with(this) {
            Vacancy(
                id = id ?: "",
                iconUri = employer?.logo_urls?.url240 ?: "",
                title = name ?: "",
                company = employer?.name ?: "",
                salary = createSalary(salary) ?: context.getString(R.string.empty_salary),
                area = area?.name ?: "",
                date = published_at ?: "",
            )
        }
    }
    
    private fun createSalary(salary: Salary?): String? {
        if (salary == null) return null
        val result = StringBuilder()
        if (salary.from != null) {
            result.append(context.getString(R.string.from))
            result.append(" ")
            result.append(salary.from)
            result.append(" ")
        }
        if (salary.to != null) {
            result.append(context.getString(R.string.to))
            result.append(" ")
            result.append(salary.to)
        }
        return result.toString()
    }
}