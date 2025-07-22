package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.vacancy.data.VacancyRepositoryImpl
import ru.practicum.android.diploma.vacancy.domain.interactor.VacancyInteractor
import ru.practicum.android.diploma.vacancy.domain.repository.VacancyRepository
import ru.practicum.android.diploma.vacancy.ui.viewmodel.VacancyViewModel

val vacancyModule = module {

    single<VacancyRepository> {
        VacancyRepositoryImpl(
            networkClient = get(),
            favoriteVacancyDao = get(),
            internetConnectionChecker = get()
        )
    }

    single {
        VacancyInteractor(get())
    }

    viewModel { (vacancyId: String) ->
        VacancyViewModel(vacancyId, get(), get())
    }
}
