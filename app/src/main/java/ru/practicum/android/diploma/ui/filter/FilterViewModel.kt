package ru.practicum.android.diploma.ui.filter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.Industries
import ru.practicum.android.diploma.util.HH_LOG

class FilterViewModel(
    private val interactor: IndustriesInteractor
) : ViewModel() {

    // Это тестовый запрос
    fun getIndustries() {
        viewModelScope.launch {
            interactor.getIndustries().collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    // Это тестовый вывод в лог!
    private fun processResult(industries: List<Industries>?, error: Int?) {
        if (industries != null) {
            Log.d(HH_LOG, "Industries count: ${industries.size}")
            for (indus in industries) {
                Log.d(HH_LOG, "Industries: ${indus.id} ${indus.name}")
                for (detail in indus.industries) {
                    Log.d(HH_LOG, "    IndustriesDetail: ${detail.id} - ${detail.name}")
                }
            }
        }
        if (error != null) {
            Log.d(HH_LOG, "Error: $error")
        }
    }
}
