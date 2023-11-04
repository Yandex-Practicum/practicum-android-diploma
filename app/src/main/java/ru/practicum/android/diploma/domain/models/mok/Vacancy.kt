package ru.practicum.android.diploma.domain.models.mok

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Vacancy(
    val id: String?,
    val premium: Boolean,
    val name: String?,
    val department: String?,
    @SerializedName("has_test")
    val hasTest: Boolean,
    @SerializedName("response_letter_required")
    val responseLetterRequired: Boolean,
    val area: Area?,
    val salary: Salary?,
    val type: Type?,
    val address: String?,
    @SerializedName("response_url")
    val responseUrl: String?,
    @SerializedName("sort_point_distance")
    val sortPointDistance: String?,
    @SerializedName("published_at")
    val publishedAt: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    val archived: Boolean,
    @SerializedName("apply_alternate_url")
    val applyAlternateUrl: String?,
    @SerializedName("show_logo_in_search")
    val showLogoInSearch: String?,
    @SerializedName("insider_interview")
    val insiderInterview: String?,
    val url: String?,
    @SerializedName("alternate_url")
    val alternateUrl: String?,
    val relations: List<String>?,
    val employer: EmployerModel?,
    val snippet: Snippet?,
    val schedule: Schedule?,
    @SerializedName("working_days")
    val workingDays: List<WorkingDay>?,
    @SerializedName("working_time_intervals")
    val workingTimeIntervals: List<WorkingTimeInterval>?,
    @SerializedName("working_time_modes")
    val workingTimeModes: List<WorkingTimeMode>?,
    @SerializedName("accept_temporary")
    val acceptTemporary: Boolean,
    @SerializedName("professional_roles")
    val professionalRoles: List<ProfessionalRole>?,
    @SerializedName("accept_incomplete_resumes")
    val acceptIncompleteResumes: Boolean,
    val experience: Experience?,
    val employment: Employment?,
    @SerializedName("adv_response_url")
    val advResponseUrl: String?,
    @SerializedName("is_adv_vacancy")
    val isAdvVacancy: Boolean
)

data class Area(
    val id: String,
    val name: String,
    val url: String
)

data class Salary(
    val from: Int,
    val to: Int,
    val currency: String,
    val gross: Boolean
)

data class Type(
    val id: String,
    val name: String
)

data class EmployerModel(
    val id: String,
    val name: String,
    val url: String,
    @SerializedName("alternate_url")
    val alternateUrl: String,
    @SerializedName("logo_urls")
    val logoUrls: LogoUrls,
    @SerializedName("vacancies_url")
    val vacanciesUrl: String,
    @SerializedName("accredited_it_employer")
    val accreditedItEmployer: Boolean,
    val trusted: Boolean
)

data class LogoUrls(
    @SerializedName("photo_resolution_90")
    val photoResolution90: String,
    val original: String,
    @SerializedName("photo_resolution_240")
    val photoResolution240: String
)

data class Snippet(
    val requirement: String,
    val responsibility: String
)

data class Schedule(
    val id: String,
    val name: String
)

data class WorkingDay(
    val id: String,
    val name: String
)

data class WorkingTimeInterval(
    val id: String,
    val name: String
)

data class WorkingTimeMode(
    val id: String,
    val name: String
)

data class ProfessionalRole(
    val id: String,
    val name: String
)

data class Experience(
    val id: String,
    val name: String
)

data class Employment(
    val id: String,
    val name: String
)

fun parseVacancy(jsonStr: String): Vacancy? {
    val gson = Gson()
    return gson.fromJson(jsonStr, Vacancy::class.java)
}

fun main() {
    val jsonStr = """
        {
            "id": "88913459",
            "premium": false,
            "name": "Водитель категории В",
            "department": null,
            "has_test": false,
            "response_letter_required": false,
            "area": {
                "id": "2",
                "name": "Санкт-Петербург",
                "url": "https://api.hh.ru/areas/2"
            },
            "salary": {
                "from": 82000,
                "to": 129000,
                "currency": "RUR",
                "gross": false
            },
            "type": {
                "id": "open",
                "name": "Открытая"
            },
            "address": null,
            "response_url": null,
            "sort_point_distance": null,
            "published_at": "2023-11-01T08:15:16+0300",
            "created_at": "2023-11-01T08:15:16+0300",
            "archived": false,
            "apply_alternate_url": "https://hh.ru/applicant/vacancy_response?vacancyId=88913459",
            "show_logo_in_search": null,
            "insider_interview": null,
            "url": "https://api.hh.ru/vacancies/88913459?host=hh.ru",
            "alternate_url": "https://hh.ru/vacancy/88913459",
            "relations": [],
            "employer": {
                "id": "5892729",
                "name": "RabotaHR",
                "url": "https://api.hh.ru/employers/5892729",
                "alternate_url": "https://hh.ru/employer/5892729",
                "logo_urls": {
                    "photo_resolution_90": "https://hhcdn.ru/employer-logo/6225262.png",
                    "original": "https://hhcdn.ru/employer-logo-original/1151200.png",
                    "photo_resolution_240": "https://hhcdn.ru/employer-logo/6225263.png"
                },
                "vacancies_url": "https://api.hh.ru/vacancies?employer_id=5892729",
                "accredited_it_employer": false,
                "trusted": true
            },
            "snippet": {
                "requirement": "Можно БЕЗ ОПЫТА.",
                "responsibility": "Доставка заказа клиенту."
            },
            "schedule": {
                "id": "flexible",
                "name": "Гибкий график"
            },
            "working_days": [
                {
                    "id": "only_saturday_and_sunday",
                    "name": "Работа только по сб и вс"
                }
            ],
            "working_time_intervals": [
                {
                    "id": "from_four_to_six_hours_in_a_day",
                    "name": "Можно работать сменами по 4–6 часов в день"
                }
            ],
            "working_time_modes": [
                {
                    "id": "start_after_sixteen",
                    "name": "Можно начинать работать после 16:00"
                }
            ],
            "accept_temporary": true,
            "professional_roles": [
                {
                    "id": "21",
                    "name": "Водитель"
                }
            ],
            "accept_incomplete_resumes": true,
            "experience": {
                "id": "noExperience",
                "name": "Нет опыта"
            },
            "employment": {
                "id": "part",
                "name": "Частичная занятость"
            },
            "adv_response_url": null,
            "is_adv_vacancy": false
        }
    """

    val vacancy = parseVacancy(jsonStr)
    println(vacancy)
}

