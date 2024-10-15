package ru.practicum.android.diploma.filter.filter.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.practicum.android.diploma.data.sp.api.FilterSp
import ru.practicum.android.diploma.filter.filter.data.repositoryimpl.FilterSPRepositoryImpl
import ru.practicum.android.diploma.filter.filter.domain.repository.FilterSPRepository
import ru.practicum.android.diploma.filter.filter.domain.usecase.impl.FilterSPInteractor
import ru.practicum.android.diploma.filter.filter.domain.usecase.impl.FilterSPInteractorImpl
import ru.practicum.android.diploma.filter.filter.presentation.viewmodel.FilterViewModel

val filterModule = module {

//todo finish? добавить FilterSp??

    singleOf(::FilterSPRepositoryImpl) bind FilterSPRepository::class
    singleOf(::FilterSPInteractorImpl) bind FilterSPInteractor::class

    viewModelOf(::FilterViewModel) //в аду я видел крутиться и жонглировать геты новых/старых параметров
}
