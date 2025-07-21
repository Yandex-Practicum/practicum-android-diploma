package ru.practicum.android.diploma.di
import org.koin.dsl.module
import ru.practicum.android.diploma.data.favourite.FavouriteRepositoryImpl
import ru.practicum.android.diploma.domain.favourite.FavouriteRepository


val repository = module {
    single<FavouriteRepository> {
        FavouriteRepositoryImpl(
            get()
        )
    }
}
