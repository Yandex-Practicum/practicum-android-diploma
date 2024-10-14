package ru.practicum.android.diploma.filter.profession.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.profession.data.mappers.IndustryMapper
import ru.practicum.android.diploma.filter.profession.data.repositoryimpl.ProfessionRepositoryImpl
import ru.practicum.android.diploma.filter.profession.domain.repository.ProfessionRepository
import ru.practicum.android.diploma.filter.profession.domain.usecase.ProfessionInteractor
import ru.practicum.android.diploma.filter.profession.domain.usecase.impl.ProfessionInteractorImpl
import ru.practicum.android.diploma.filter.profession.presentation.viewmodel.ProfessionViewModel

val professionModule = module {

    singleOf(::ProfessionInteractorImpl) bind ProfessionInteractor::class
    singleOf(::ProfessionRepositoryImpl) bind ProfessionRepository::class
    singleOf(::IndustryMapper)

    viewModelOf(::ProfessionViewModel)
}
