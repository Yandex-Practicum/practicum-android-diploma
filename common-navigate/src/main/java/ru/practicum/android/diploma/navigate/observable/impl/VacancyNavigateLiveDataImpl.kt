package ru.practicum.android.diploma.navigate.observable.impl

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ru.practicum.android.diploma.navigate.observable.VacancyNavigateLiveData
import ru.practicum.android.diploma.navigate.state.NavigateEventState

internal class VacancyNavigateLiveDataImpl(): VacancyNavigateLiveData {

    private val _navigateEventState = MutableLiveData<NavigateEventState>()

    override fun observeNavigateEventState(owner: LifecycleOwner, observer: Observer<NavigateEventState>) {
        _navigateEventState.observe(owner, observer)
    }

    override fun toVacancySourceDataDb(id: Int) {
        _navigateEventState.value = NavigateEventState.ToVacancyDataSourceDb(id)
    }

    override fun toVacancySourceDataNetwork(id: String) {
        _navigateEventState.value = NavigateEventState.ToVacancyDataSourceNetwork(id)
    }

    override fun toFilter() {
        _navigateEventState.value = NavigateEventState.ToFilter
    }
}
