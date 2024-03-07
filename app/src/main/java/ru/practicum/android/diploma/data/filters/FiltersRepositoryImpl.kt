package ru.practicum.android.diploma.data.filters

import android.content.Context
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.models.Filter

class FiltersRepositoryImpl(
    private val context: Context
) : FiltersRepository {

    private val prefs = context.getSharedPreferences("filters", Context.MODE_PRIVATE)

    override fun setFilter(filter: Filter) {
        prefs
            .edit()
            .putString(FILTER_KEY, Gson().toJson(filter))
            .apply()
    }

    override fun getFilters(): Filter {
        val filterGson = prefs.getString(FILTER_KEY, "").takeIf { it?.isNotEmpty() ?: false }
        return filterGson?.let {
            Gson().fromJson(filterGson, Filter::class.java)
        } ?: Filter()
    }

    companion object {
        private const val FILTER_KEY = "filter"
    }
}
