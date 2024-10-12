package ru.practicum.android.diploma.navigate.observable

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import ru.practicum.android.diploma.navigate.state.NavigateEventState

interface VacancyNavigateLiveData {

    fun observeNavigateEventState(
        owner: LifecycleOwner,
        observer: Observer<NavigateEventState>
    )

    fun toVacancySourceDataDb(id: Int)

    fun toVacancySourceDataNetwork(id: String)

    fun toFilter()
}
