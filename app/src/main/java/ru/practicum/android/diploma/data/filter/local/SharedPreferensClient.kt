package ru.practicum.android.diploma.data.filter.local

import android.content.SharedPreferences
import com.google.gson.Gson

class SharedPreferensClient(val gson: Gson, private val sharedPreferences: SharedPreferences) :
    LocalStorage {
    override fun setSalary (salary: String) {
        sharedPreferences.edit()
            .putString(SALARY_KEY, salary)
            .apply()
    }

    override fun getSalary(): String {
       return sharedPreferences.getString(SALARY_KEY, null) ?: ""
    }

    companion object{
        const val SALARY_KEY = "salary"
    }
}