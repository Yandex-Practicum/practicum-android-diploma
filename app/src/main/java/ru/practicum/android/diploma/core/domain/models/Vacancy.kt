package ru.practicum.android.diploma.core.domain.models

data class Vacancy( // Используется в поиске и в избранных
    val id: String,
    val name: String, // в формате "Android-разработчик, Москва"
    val employerName: String,
    val employerLogoUrl: String?,
    val salary: String, // в формате "от 1 000 до 1 500 €"
) {
    companion object
}

typealias Vacancies = List<Vacancy>
