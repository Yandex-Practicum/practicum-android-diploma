package ru.practicum.android.diploma.data.filter

import ru.practicum.android.diploma.data.filter.local.LocalStorage
import ru.practicum.android.diploma.domain.models.filter.FilterRepository

class FilterRepositoryImpl(val locale: LocalStorage):FilterRepository {
    override fun setSalary(input:String){
        locale.setSalary(input)
    }

    override fun getSalary(): String {
        return locale.getSalary()
    }
}