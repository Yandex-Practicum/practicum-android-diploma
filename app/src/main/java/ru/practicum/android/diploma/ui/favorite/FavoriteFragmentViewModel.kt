package ru.practicum.android.diploma.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.models.vacacy.Employer
import ru.practicum.android.diploma.domain.models.vacacy.Salary
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy
import ru.practicum.android.diploma.domain.models.vacacy.VacancyArea
import ru.practicum.android.diploma.domain.models.vacacy.VacancyType


class FavoriteFragmentViewModel() : ViewModel() {

    private var state = MutableLiveData<FavoriteFragmentUpdate>(

        //FavoriteFragmentUpdate.EmptyVacancyList

        FavoriteFragmentUpdate.VacancyList(

            listOf(
                Vacancy(
                    id = "id",
                    department = null,
                    name = "job",
                    area = VacancyArea(
                        id = "id",
                        name = "area_name",
                        url = "url"
                    ),
                    employer = Employer(
                        id = "id",
                        logoUrls = null,
                        name = "BASED DEPARTMENT",
                        trusted = true,
                        vacanciesUrl = null
                    ),
                    salary = Salary(
                        currency = null,
                        from = null,
                        to = null,
                        gross = false
                    ),
                    type = VacancyType(
                        id = "id",
                        name = "asd"
                    )
                )
            )

        )
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
