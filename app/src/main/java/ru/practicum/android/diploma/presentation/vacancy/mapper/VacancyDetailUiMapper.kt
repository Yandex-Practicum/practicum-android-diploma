package ru.practicum.android.diploma.presentation.vacancy.mapper

import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.presentation.vacancy.model.VacancyDetailContentUi
import ru.practicum.android.diploma.util.extentions.SalaryFormatLabels
import ru.practicum.android.diploma.util.extentions.formatHtmlDescription
import ru.practicum.android.diploma.util.extentions.formatSalary

fun VacancyDetail.toContentUi(labels: SalaryFormatLabels): VacancyDetailContentUi {
    return VacancyDetailContentUi(
        title = name,
        salaryText = salary.formatSalary(labels),
        descriptionText = description.formatHtmlDescription(),
    )
}
