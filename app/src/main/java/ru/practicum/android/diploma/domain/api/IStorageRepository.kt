package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.SearchVacanciesParam

interface IStorageRepository {
    fun read(): SearchVacanciesParam
    fun write(filterParam: SearchVacanciesParam)
}
