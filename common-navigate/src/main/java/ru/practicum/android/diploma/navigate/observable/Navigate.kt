package ru.practicum.android.diploma.navigate.observable

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

interface Navigate<T> {

    fun observeNavigateEventState(
        owner: LifecycleOwner,
        observer: Observer<T>
    )

    fun navigateTo(state: T)

}
