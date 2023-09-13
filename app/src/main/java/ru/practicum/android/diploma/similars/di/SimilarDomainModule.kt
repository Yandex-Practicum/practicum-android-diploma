package ru.practicum.android.diploma.similars.di

import dagger.Binds
import dagger.Module
import ru.practicum.android.diploma.similars.data.SimilarRepositoryImpl
import ru.practicum.android.diploma.similars.domain.SimilarRepository
import ru.practicum.android.diploma.similars.domain.api.GetSimilarVacanciesUseCase
import ru.practicum.android.diploma.similars.domain.impl.GetSimilarVacanciesUseCaseImpl

@Module
interface SimilarDomainModule {
    @Binds
    fun bindGetSimilarVacanciesUseCase(getSimilarVacanciesUseCaseImpl: GetSimilarVacanciesUseCaseImpl): GetSimilarVacanciesUseCase
    @Binds
    fun bindSimilarRepository(similarRepositoryImpl: SimilarRepositoryImpl): SimilarRepository
}