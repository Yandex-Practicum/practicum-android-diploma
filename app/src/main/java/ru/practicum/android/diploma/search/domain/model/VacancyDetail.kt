package ru.practicum.android.diploma.search.domain.model

data class VacancyDetail(
    val id: String, // +
    val name: String, // +
    val description: String, // описание вакансии, обязанности
    val salary: Salary?, // +
    val address: Address?, // +
    val experience: Experience?, // требуемый опыт
    val schedule: Schedule?, // в офисе пон-пят
    val employment: Employment?, // полная занятость//ок
    val contacts: Contacts?, // +
    val employer: Employer, // Работодатель
    val area: FilterArea, // +
    val skills: List<String>, // ключевые навыки
    val url: String, // null+
    val industry: FilterIndustry // null//где выводится?
)
