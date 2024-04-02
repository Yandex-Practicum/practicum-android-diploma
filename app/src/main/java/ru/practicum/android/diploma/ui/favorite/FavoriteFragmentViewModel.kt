package ru.practicum.android.diploma.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy

class FavoriteFragmentViewModel : ViewModel() {

    private var state = MutableLiveData<FavoriteFragmentUpdate>(
        FavoriteFragmentUpdate.EmptyVacancyList
    )

    fun getState(): LiveData<FavoriteFragmentUpdate> {
        return state
    }
}

sealed class FavoriteFragmentUpdate {

    data object EmptyVacancyList : FavoriteFragmentUpdate()

    data object GetVacanciesError : FavoriteFragmentUpdate()

    data class VacancyList(
        val vacancies: List<Vacancy>
    ) : FavoriteFragmentUpdate()
}
