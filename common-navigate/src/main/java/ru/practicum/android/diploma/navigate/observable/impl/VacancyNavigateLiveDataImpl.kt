package ru.practicum.android.diploma.navigate.observable.impl

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ru.practicum.android.diploma.navigate.observable.VacancyNavigateLiveData

internal class VacancyNavigateLiveDataImpl<T> : VacancyNavigateLiveData<T> {

    private val _navigateEventState = MutableLiveData<T>()

    override fun observeNavigateEventState(owner: LifecycleOwner, observer: Observer<T>) {
        _navigateEventState.observe(owner, observer)
    }

    override fun navigateTo(state: T) {
        _navigateEventState.value = state
    }
}
