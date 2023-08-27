package ru.practicum.android.diploma.details.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.practicum.android.diploma.details.data.DetailsRepositoryImpl
import ru.practicum.android.diploma.details.data.db.FavoriteVacanciesDb
import ru.practicum.android.diploma.details.domain.DetailsRepository
import ru.practicum.android.diploma.di.annotations.ApplicationScope

@Module
class DetailsDataModule {
    @ApplicationScope
    @Provides
     fun bindDetailsRepository(detailsRepositoryImpl: DetailsRepositoryImpl): DetailsRepository{
         return detailsRepositoryImpl
     }

    @ApplicationScope
    @Provides
    fun provideDataBase(context: Context): FavoriteVacanciesDb {
        return Room.databaseBuilder(
            context,
            FavoriteVacanciesDb::class.java,
            "favorite_vacancies.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    companion object {

    }
}
