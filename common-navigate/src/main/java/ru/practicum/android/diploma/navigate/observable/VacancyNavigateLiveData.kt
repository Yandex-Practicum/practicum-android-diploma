package ru.practicum.android.diploma.navigate.observable

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.practicum.android.diploma.navigate.state.NavigateEventState

class VacancyNavigateLiveData {
    private val _navigateEventState = MutableLiveData<NavigateEventState>()
    val navigateEventState: LiveData<NavigateEventState> get() = _navigateEventState

    fun toVacancySourceDataDb(id: Int) {
        _navigateEventState.value = NavigateEventState.ToVacancyDataSourceDb(id)
    }

    fun toVacancySourceDataNetwork(id: String) {
        _navigateEventState.value = NavigateEventState.ToVacancyDataSourceNetwork(id)
    }

    fun toFilter() {
        _navigateEventState.value = NavigateEventState.ToFilter
    }
}
