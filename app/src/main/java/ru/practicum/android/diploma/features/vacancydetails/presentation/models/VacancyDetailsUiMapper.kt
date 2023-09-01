package ru.practicum.android.diploma.features.vacancydetails.presentation.models

import ru.practicum.android.diploma.features.vacancydetails.domain.models.Salary
import ru.practicum.android.diploma.features.vacancydetails.domain.models.VacancyDetails

class VacancyDetailsUiMapper : (VacancyDetails) -> VacancyDetailsUiModel {

    override fun invoke(model: VacancyDetails): VacancyDetailsUiModel {
        return VacancyDetailsUiModel(
            vacancyId = model.vacancyId,
            vacancyName = model.vacancyName,
            salary = model.salary?.let { formatSalaryString(it) } ?: "",
            logoUrl = model.logoUrl,
            employerName = model.employerName,
            employerArea = model.employerArea,
            experience = model.experienceReq,
            employmentTypes = formatEmploymentTypes(model.employmentType, model.scheduleType),
            vacancyDescription = chooseDescription(
                model.vacancyDescription,
                model.vacancyBrandedDesc
            ),
            keySkills = formatKeySkills(model.keySkills),
            contactsName = model.contactsName,
            contactsEmail = model.contactsEmail,
            contactsPhones = model.contactsPhones,
            responseUrl = model.responseUrl
        )
    }

    private fun formatKeySkills(keySkills: List<String>): String {
        return keySkills
            .filter { it.isNotEmpty() }
            .joinToString("\n")
    }

    private fun chooseDescription(vacancyDescription: String, vacancyBrandedDesc: String): String {
        return if (vacancyBrandedDesc.isNotEmpty()) return vacancyBrandedDesc else vacancyDescription
    }

    private fun formatEmploymentTypes(employmentType: String, scheduleType: String): String {
        return listOf(employmentType, scheduleType)
            .filter { it.isNotEmpty() }
            .joinToString(", ")
    }

    private fun formatSalaryString(salaryObj: Salary): String {
        val salaryString = ""
//        if (salaryObj.salaryCurrency.isNullOrEmpty()) {
//            return ""
//        } else if (salaryObj.salaryLowerBoundary != null) {
//            return ""
//        }
        return salaryString
    }

}