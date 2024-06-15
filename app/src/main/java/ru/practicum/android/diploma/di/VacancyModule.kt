package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.repository.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.data.sharing.ExternalNavigatorImpl
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.api.sharing.ExternalNavigator
import ru.practicum.android.diploma.domain.impl.details.VacancyDetailsInteractorImpl
import ru.practicum.android.diploma.ui.details.VacancyDetailsViewModel

val vacancyModule = module {
    single<VacancyDetailsRepository> { VacancyDetailsRepositoryImpl(get(), get(), get()) }
    factory<VacancyDetailsInteractor> { VacancyDetailsInteractorImpl(get()) }
    viewModel { VacancyDetailsViewModel(get(), get(), get(), get()) }
    single<ExternalNavigator> { ExternalNavigatorImpl(get()) }
}
