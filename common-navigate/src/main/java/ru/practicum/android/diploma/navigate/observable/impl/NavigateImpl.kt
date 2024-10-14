package ru.practicum.android.diploma.navigate.observable.impl

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ru.practicum.android.diploma.navigate.observable.Navigate

internal class NavigateImpl<T> : Navigate<T> {

    private val _navigateEventState = MutableLiveData<T>()

    override fun observeNavigateEventState(owner: LifecycleOwner, observer: Observer<T>) {
        _navigateEventState.observe(owner, observer)
    }

    override fun navigateTo(state: T) {
        _navigateEventState.value = state
    }
}
