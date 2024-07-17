package ru.practicum.android.diploma.filter.data

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.filter.domain.api.FilterStoreRepository
import ru.practicum.android.diploma.filter.domain.models.Filter

class FilterStoreRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
) : FilterStoreRepository {

    override fun save(filter: Filter) {
        val json = gson.toJson(filter, Filter::class.java)
        sharedPreferences
            .edit()
            .putString(FILTER_STORE_KEY, json)
            .apply()
    }

    override fun load(): Filter {
        val json = sharedPreferences.getString(FILTER_STORE_KEY, null) ?: return Filter()
        return gson.fromJson(json, Filter::class.java)
    }

    private companion object {
        const val FILTER_STORE_KEY = "filter"
    }
}
