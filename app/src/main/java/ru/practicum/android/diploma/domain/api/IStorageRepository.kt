package ru.practicum.android.diploma.domain.api

interface IStorageRepository {
    fun read(): List<Vacancy>
    fun write()
}
