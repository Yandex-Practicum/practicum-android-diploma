package ru.practicum.android.diploma.search.data.mapper

import ru.practicum.android.diploma.search.data.dto.AddressDto
import ru.practicum.android.diploma.search.data.dto.ContactsDto
import ru.practicum.android.diploma.search.data.dto.EmployerDto
import ru.practicum.android.diploma.search.data.dto.EmploymentDto
import ru.practicum.android.diploma.search.data.dto.ExperienceDto
import ru.practicum.android.diploma.search.data.dto.FilterAreaDto
import ru.practicum.android.diploma.search.data.dto.FilterIndustryDto
import ru.practicum.android.diploma.search.data.dto.SalaryDto
import ru.practicum.android.diploma.search.data.dto.ScheduleDto
import ru.practicum.android.diploma.search.data.dto.VacancyDetailDto
import ru.practicum.android.diploma.search.data.dto.VacancyResponseDto
import ru.practicum.android.diploma.search.domain.model.Address
import ru.practicum.android.diploma.search.domain.model.Contacts
import ru.practicum.android.diploma.search.domain.model.Employer
import ru.practicum.android.diploma.search.domain.model.Employment
import ru.practicum.android.diploma.search.domain.model.Experience
import ru.practicum.android.diploma.search.domain.model.FilterArea
import ru.practicum.android.diploma.search.domain.model.FilterIndustry
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.Schedule
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.search.domain.model.VacancyResponse

class DtoMapper {
    fun vacancyDetailDtoToDomain(vacancyDetailDto: VacancyDetailDto): VacancyDetail =
        VacancyDetail(
            id = vacancyDetailDto.id,
            name = vacancyDetailDto.name,
            description = vacancyDetailDto.description,
            salary = salaryDtoToDomain(vacancyDetailDto.salary),
            address = addressDtoToDomain(vacancyDetailDto.address),
            experience = experienceDtoToDomain(vacancyDetailDto.experience),
            schedule = scheduleDtoToDto(vacancyDetailDto.schedule),
            employment = employmentDto(vacancyDetailDto.employment),
            contacts = contactsDtoToDomain(vacancyDetailDto.contacts),
            employer = employerDtoToDomain(vacancyDetailDto.employer),
            area = filterAreaDtoToDomain(vacancyDetailDto.area),
            skills = vacancyDetailDto.skills,
            url = vacancyDetailDto.url,
            industry = filterIndustryDtoToDomain(vacancyDetailDto.industry)
        )

    fun salaryDtoToDomain(salaryDto: SalaryDto?): Salary? {
        return if (salaryDto == null) {
            null
        } else {
            Salary(
                salaryDto.from,
                salaryDto.to,
                salaryDto.currency
            )
        }
    }

    fun addressDtoToDomain(addressDto: AddressDto?): Address? {
        return if (addressDto == null) {
            null
        } else {
            Address(
                addressDto.city,
                addressDto.street,
                addressDto.building,
                addressDto.fullAddress
            )
        }
    }

    fun experienceDtoToDomain(experienceDto: ExperienceDto?): Experience? {
        return if (experienceDto == null) {
            null
        } else {
            Experience(
                experienceDto.id,
                experienceDto.name
            )
        }
    }

    fun scheduleDtoToDto(scheduleDto: ScheduleDto?): Schedule? {
        return if (scheduleDto == null) {
            null
        } else {
            Schedule(
                scheduleDto.id,
                scheduleDto.name
            )
        }
    }

    fun employmentDto(employmentDto: EmploymentDto?): Employment? {
        return if (employmentDto == null) {
            null
        } else {
            Employment(
                employmentDto.id,
                employmentDto.name
            )
        }
    }

    fun employerDtoToDomain(employerDto: EmployerDto): Employer =
        Employer(
            employerDto.id,
            employerDto.name,
            employerDto.logo
        )

    fun contactsDtoToDomain(contactsDto: ContactsDto?): Contacts? {
        return if (contactsDto == null) {
            null
        } else {
            Contacts(
                id = contactsDto.id,
                name = contactsDto.name,
                email = contactsDto.email,
                phone = contactsDto.phones.map { phoneDto ->
                    val comment = phoneDto.comment?.takeIf { it.isNotBlank() }
                    if (comment != null) {
                        "${phoneDto.formatted} ($comment)"
                    } else {
                        phoneDto.formatted
                    }
                }
            )
        }
    }

    fun filterIndustryDtoToDomain(filterIndustryDto: FilterIndustryDto): FilterIndustry =
        FilterIndustry(
            filterIndustryDto.id,
            filterIndustryDto.name
        )

    fun filterAreaDtoToDomain(filterAreaDto: FilterAreaDto): FilterArea =
        FilterArea(
            filterAreaDto.id,
            filterAreaDto.name,
            filterAreaDto.parentId,
            filterAreaDto.areas.map { FilterArea(it.id, it.name, it.parentId, emptyList()) }
        )

    fun vacancyResponseDtoToDomain(vacancyResponseDto: VacancyResponseDto): VacancyResponse =
        VacancyResponse(
            vacancyResponseDto.found,
            vacancyResponseDto.pages,
            vacancyResponseDto.page,
            vacancyResponseDto.vacancies?.map {
                vacancyDetailDtoToDomain(it)
            } ?: emptyList()
        )

}
