package ru.practicum.android.diploma.search.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.filter.data.repositoryimpl.DtoToModelMapper
import ru.practicum.android.diploma.filter.filter.data.repositoryimpl.FilterSPRepositoryImpl
import ru.practicum.android.diploma.filter.filter.data.repositoryimpl.ModelToDtoMapper
import ru.practicum.android.diploma.filter.filter.domain.repository.FilterSPRepository
import ru.practicum.android.diploma.filter.filter.domain.usecase.impl.FilterSPInteractor
import ru.practicum.android.diploma.filter.filter.domain.usecase.impl.FilterSPInteractorImpl
import ru.practicum.android.diploma.filter.filter.presentation.viewmodel.FilterViewModel
import ru.practicum.android.diploma.search.data.repositoryimpl.network.VacanciesRepositoryImpl
import ru.practicum.android.diploma.search.domain.repository.VacanciesRepository
import ru.practicum.android.diploma.search.domain.usecase.VacanciesInteractor
import ru.practicum.android.diploma.search.domain.usecase.impl.VacanciesInteractorImpl
import ru.practicum.android.diploma.search.presentation.viewmodel.VacancyListViewModel
import ru.practicum.android.diploma.search.util.AreaConverter
import ru.practicum.android.diploma.search.util.IndustryConverter
import ru.practicum.android.diploma.search.util.VacancyConverter

val searchModule = module {
    viewModelOf(::FilterViewModel)
    singleOf(::FilterSPInteractorImpl) bind FilterSPInteractor::class
    singleOf(::FilterSPRepositoryImpl) bind FilterSPRepository::class
    singleOf(::DtoToModelMapper)
    singleOf(::ModelToDtoMapper)
    singleOf(::FilterSPRepositoryImpl)

    viewModelOf(::VacancyListViewModel)

    single {
        AreaConverter()
    }

    single {
        IndustryConverter()
    }

    single {
        VacancyConverter()
    }

    single<VacanciesInteractor> {
        VacanciesInteractorImpl(get())
    }

    single<VacanciesRepository> {
        VacanciesRepositoryImpl(get(), get(), get(), get(), androidContext())
    }

}
