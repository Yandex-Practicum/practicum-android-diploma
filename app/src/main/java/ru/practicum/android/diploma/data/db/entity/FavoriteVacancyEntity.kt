package ru.practicum.android.diploma.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancies_table")
data class FavoriteVacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    @Embedded(prefix = "salary_")
    val salary: SalaryEmbedded?,
    @Embedded(prefix = "address_")
    val address: AddressEmbedded?,
    @Embedded(prefix = "experience_")
    val experience: ExperienceEmbedded?,
    @Embedded(prefix = "schedule_")
    val schedule: ScheduleEmbedded?,
    @Embedded(prefix = "employment_")
    val employment: EmploymentEmbedded?,
    @Embedded(prefix = "contacts_")
    val contacts: ContactsEmbedded?,
    @Embedded(prefix = "employer_")
    val employer: EmployerEmbedded,
    @Embedded(prefix = "area_")
    val area: FilterAreaEmbedded,
    val skills: List<String>?,
    val url: String,
    @Embedded(prefix = "industry_")
    val industry: FilterIndustryEmbedded
)

data class SalaryEmbedded(
    val from: Int?,
    val to: Int?,
    val currency: String?
)

data class AddressEmbedded(
    val id: String,
    val city: String,
    val street: String,
    val building: String,
    val raw: String
)

data class ExperienceEmbedded(
    val id: String,
    val name: String
)

data class ScheduleEmbedded(
    val id: String,
    val name: String
)

data class EmploymentEmbedded(
    val id: String,
    val name: String
)

data class ContactsEmbedded(
    val id: String,
    val name: String,
    val email: String,
    val phones: List<PhoneEmbedded>
)

data class PhoneEmbedded(
    val comment: String?,
    val formatted: String
)

data class EmployerEmbedded(
    val id: String,
    val name: String,
    val logo: String
)

data class FilterIndustryEmbedded(
    val id: Int,
    val name: String
)

data class FilterAreaEmbedded(
    val id: Int,
    val name: String,
    val parentId: Int?,
    val areas: List<FilterAreaEmbedded>
)
