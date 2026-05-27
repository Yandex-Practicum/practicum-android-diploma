package ru.practicum.android.diploma.vacancy.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.practicum.android.diploma.vacancy.data.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.vacancy.data.network.VacancyApi
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsRepository
import ru.practicum.android.diploma.vacancy.domain.api.VacancyInteractor
import ru.practicum.android.diploma.vacancy.domain.impl.VacancyInteractorImpl
import ru.practicum.android.diploma.vacancy.ui.VacancyViewModel
import ru.practicum.android.diploma.vacancy.ui.VacancyViewModelImpl

val vacancyModule = module {
    single<VacancyApi> {
        get<Retrofit>().create(VacancyApi::class.java)
    }
    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get(), get(), get())
    }
    single<VacancyInteractor> {
        VacancyInteractorImpl(get())
    }
    viewModel<VacancyViewModel> { params ->
        VacancyViewModelImpl(params.get(), get())
    }
}
