package ru.practicum.android.diploma.features.vacancydetails.data.models

import ru.practicum.android.diploma.features.vacancydetails.domain.models.Salary
import ru.practicum.android.diploma.features.vacancydetails.domain.models.VacancyDetails

class VacancyDetailsMapper : (VacancyDetailsResponse) -> VacancyDetails {

    override fun invoke(response: VacancyDetailsResponse): VacancyDetails {
        return with(response.searchResult) {
            VacancyDetails(
                vacancyId = this.vacancyId,
                vacancyName = this.vacancyName,
                salary = getSalary(this.salary),
                logoUrl = this.employer?.logoUrls?.logoUrl90 ?: "",
                employerName = this.employer?.employerName ?: "",
                employerArea = this.vacancyArea.area,
                experienceReq = this.experience?.experience ?: "",
                employmentType = this.employmentType?.employmentTypeName ?: "",
                scheduleType = this.scheduleType?.scheduleTypeName ?: "",
                vacancyDescription = this.vacancyDesc,
                vacancyBrandedDesc = this.vacancyBrandedDesc ?: "",
                keySkills = this.keySkills,
                contactsName = this.contacts?.contactsName ?: "",
                contactsEmail = this.contacts?.contactsEmail ?: "",
                contactsPhones = getPhones(this.contacts?.phones),
                responseUrl = this.responseUrl ?: ""
            )
        }
    }

    private fun getPhones(phones: List<VacancyDetailsDto.Phone>?): List<String> {
        return phones?.map {
            it.phoneNumber
        } ?: emptyList()
    }

    private fun getSalary(salaryDto: VacancyDetailsDto.SalaryDto?): Salary? {
        return when (salaryDto) {
            null -> null
            else -> Salary(
                salaryCurrency = salaryDto.currency,
                salaryLowerBoundary = salaryDto.from,
                salaryUpperBoundary = salaryDto.to
            )
        }
    }

}