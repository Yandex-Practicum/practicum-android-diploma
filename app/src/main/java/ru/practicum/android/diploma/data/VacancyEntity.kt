package ru.practicum.android.diploma.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "favorite_vacancies")
data class VacancyEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "company_name")
    val companyName: String,

    @ColumnInfo(name = "company_logo")
    val companyLogo: String?,

    @ColumnInfo(name = "industry_id")
    val industryId: String?,

    @ColumnInfo(name = "industry_name")
    val industryName: String?,

    @ColumnInfo(name = "salary_from")
    val salaryFrom: Int?,

    @ColumnInfo(name = "salary_to")
    val salaryTo: Int?,

    @ColumnInfo(name = "currency")
    val currency: String?,

    @ColumnInfo(name = "area_id")
    val areaId: String?,

    @ColumnInfo(name = "area_name")
    val areaName: String,

    @ColumnInfo(name = "experience")
    val experience: String?,

    @ColumnInfo(name = "schedule")
    val schedule: String?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "skills")
    val skillsJson: String?,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
) {
    fun getSkillsList(): List<String> {
        return if (!skillsJson.isNullOrEmpty()) {
            val type = object : TypeToken<List<String>>() {}.type
            Gson().fromJson(skillsJson, type)
        } else {
            emptyList()
        }
    }
}
