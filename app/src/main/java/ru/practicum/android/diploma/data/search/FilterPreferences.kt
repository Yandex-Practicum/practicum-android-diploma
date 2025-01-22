package ru.practicum.android.diploma.data.search

interface FilterPreferences {
    suspend fun savePlace(city: String)
    suspend fun getPlace(): String?

    suspend fun saveIndustry(industry: String)
    suspend fun getIndustry(): String?

    suspend fun saveExpectedSalary(minSalary: Int)
    suspend fun getExpectedSalary(): Int?

    suspend fun saveSalaryRequired(isRequired: Boolean)
    suspend fun isSalaryRequired(): Boolean
}
