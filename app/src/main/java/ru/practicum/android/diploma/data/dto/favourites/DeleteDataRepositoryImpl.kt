package ru.practicum.android.diploma.data.dto.favourites

import ru.practicum.android.diploma.data.room.AppDatabase
import ru.practicum.android.diploma.domain.api.DeleteDataRepository

class DeleteDataRepositoryImpl(private val db: AppDatabase) : DeleteDataRepository {
    override suspend fun delete(data: String) {
        db.vacancyDao().deleteVacancy(data)
    }
}
