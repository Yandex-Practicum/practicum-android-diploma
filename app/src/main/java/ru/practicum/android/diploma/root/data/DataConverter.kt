package ru.practicum.android.diploma.root.data

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.root.domain.model.VacancyShortModel
import ru.practicum.android.diploma.root.presentation.model.VacancyScreenModel

class DataConverter(
    private val context: Context
) {
    fun map(model: VacancyShortModel): VacancyScreenModel {
        val salaryFrom = if (model.salaryFrom != null)
            context.getString(R.string.from) + " ${model.salaryFrom} "
        else ""

        val salaryTo = if (model.salaryTo != null)
            context.getString(R.string.to) + " ${model.salaryTo} "
        else ""

        val currency = model.salaryCurrency ?: ""

        var salary = salaryFrom + salaryTo + currency
        if (salaryFrom.isEmpty() && salaryTo.isEmpty()) salary = context.getString(R.string.no_salary)

        val details = "${model.jobTitle}, ${model.region}\n" + salary

        return VacancyScreenModel(
            id = model.id,
            artworkUrl = model.artworkUrl ?: "",
            employer = model.employer,
            details = details
        )
    }
}