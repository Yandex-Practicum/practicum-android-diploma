package ru.practicum.android.diploma.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.data.db.config_db.DatabaseConfig

@Entity(tableName = DatabaseConfig.VACANCY_TABLE)
data class VacancyEntity(
    @PrimaryKey
    @ColumnInfo(name = DatabaseConfig.ID_VACANCY)
    val idVacancy: Int,
    @ColumnInfo(name = DatabaseConfig.NAME_VACANCY)
    val nameVacancy: String,
    val salary: String,
    @ColumnInfo(name = DatabaseConfig.NAME_COMPANY)
    val nameCompany: String,
    val location: String,
    val experience: String,
    val employment: String,
    val description: String,
    @ColumnInfo(name = DatabaseConfig.URL_LOGO)
    val urlLogo: String?,
    @ColumnInfo(name = DatabaseConfig.DATE_ADD_VACANCY)
    val dateAddVacancy: Long = -1L,
)
