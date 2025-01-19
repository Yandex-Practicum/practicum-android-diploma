package ru.practicum.android.diploma.data

interface FilterPreferences {
    suspend fun saveCity(city: String)
    suspend fun getCity(): String?

    suspend fun saveIndustry(industry: String)
    suspend fun getIndustry(): String?

    suspend fun saveMinSalary(minSalary: Int)
    suspend fun getMinSalary(): Int?

    suspend fun saveSalaryRequired(isRequired: Boolean)
    suspend fun isSalaryRequired(): Boolean
}
