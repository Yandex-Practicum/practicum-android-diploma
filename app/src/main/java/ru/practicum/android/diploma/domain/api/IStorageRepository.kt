package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.data.dto.Vacancy

interface IStorageRepository {
    fun read(): List<Vacancy>
    fun write(vacancies: List<Vacancy>)
}
