package ru.practicum.android.diploma.ui.filter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filters.AreasInteractor
import ru.practicum.android.diploma.domain.models.Areas
import ru.practicum.android.diploma.util.HH_LOG

class FilterViewModel(
    private val interactor: AreasInteractor
) : ViewModel() {

    // Это тестовый запрос
    fun getAreas() {
        viewModelScope.launch {
            interactor.getAreas().collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    // Обработка ответа
    private fun processResult(areas: List<Areas>?, error: Int?) {
        if (areas != null) {
            printAreas(areas)
        }
        if (error != null) {
            Log.d(HH_LOG, "Error: $error")
        }
    }

    // Это тестовый вывод в лог!
    private fun printAreas(areas: List<Areas>, sep: String = "") {
        for (area in areas) {
            Log.d(HH_LOG, "$sep Areas ID: ${area.id}; Areas name: ${area.name}")
            printAreas(area.areas, "$sep    ")
        }
    }
}
