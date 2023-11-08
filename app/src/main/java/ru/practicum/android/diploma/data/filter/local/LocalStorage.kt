package ru.practicum.android.diploma.data.filter.local

interface LocalStorage {

    fun setSalary(salary: String)
    fun getSalary(): String
}
