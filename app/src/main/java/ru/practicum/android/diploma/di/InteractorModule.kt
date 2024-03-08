package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.detail.DetailInteractor
import ru.practicum.android.diploma.domain.favorite.FavoriteInteractor
import ru.practicum.android.diploma.domain.favorite.impl.FavoriteInteractorImpl
import ru.practicum.android.diploma.domain.similar.impl.SimilarInteractorImpl
import ru.practicum.android.diploma.domain.industries.IndustriesInteractor
import ru.practicum.android.diploma.domain.industries.impl.IndustriesInteractorImpl
import ru.practicum.android.diploma.domain.models.main.impl.SearchInteractorImpl
import ru.practicum.android.diploma.domain.search.SearchInteractor

val interactorModule = module {

    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }

    single<DetailInteractor> {
        SimilarInteractorImpl(get())
    }

    single<FavoriteInteractor> {
        FavoriteInteractorImpl(get())
    }

    single<IndustriesInteractor> {
        IndustriesInteractorImpl(get())
    }
}
