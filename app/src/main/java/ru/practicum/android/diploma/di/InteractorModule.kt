package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.favorite.domain.api.FavoriteVacancyInteractor
import ru.practicum.android.diploma.favorite.domain.impl.FavoriteVacancyInteractorImpl
import ru.practicum.android.diploma.filters.areas.domain.api.AreaCashInteractor
import ru.practicum.android.diploma.filters.areas.domain.api.FilterAreaInteractor
import ru.practicum.android.diploma.filters.areas.domain.impl.AreaCashInteractorImpl
import ru.practicum.android.diploma.filters.areas.domain.impl.FilterAreaInteractorImpl
import ru.practicum.android.diploma.filters.industries.domain.api.FilterIndustriesInteractor
import ru.practicum.android.diploma.filters.industries.domain.impl.FilterIndustriesInteractorImpl
import ru.practicum.android.diploma.search.domain.api.RequestBuilderInteractor
import ru.practicum.android.diploma.search.domain.api.SearchVacancyInteractor
import ru.practicum.android.diploma.search.domain.impl.RequestBuilderInteractorImpl
import ru.practicum.android.diploma.search.domain.impl.SearchVacancyInteractorImpl
import ru.practicum.android.diploma.vacancy.domain.api.GetVacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.impl.GetVacancyDetailsInteractorImpl

val interactorModule = module {

    single<SearchVacancyInteractor> {
        SearchVacancyInteractorImpl(get())
    }
    single<GetVacancyDetailsInteractor> {
        GetVacancyDetailsInteractorImpl(get())
    }

    single<FavoriteVacancyInteractor> {
        FavoriteVacancyInteractorImpl(get())
    }
    single<FilterAreaInteractor> {
        FilterAreaInteractorImpl(get())
    }

    single<FilterIndustriesInteractor> {
        FilterIndustriesInteractorImpl(get())
    }

    single<RequestBuilderInteractor> {
        RequestBuilderInteractorImpl(get())
    }

    single<AreaCashInteractor> {
        AreaCashInteractorImpl(get())
    }
}
