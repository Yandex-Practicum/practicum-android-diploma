package ru.practicum.android.diploma.details.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.practicum.android.diploma.details.data.DetailsRepositoryImpl
import ru.practicum.android.diploma.details.data.local.LocalDataSource
import ru.practicum.android.diploma.details.data.local.LocalDataSourceDetailsImpl
import ru.practicum.android.diploma.details.data.local.db.FavoriteDao
import ru.practicum.android.diploma.details.data.local.db.FavoriteVacanciesDb
import ru.practicum.android.diploma.details.domain.DetailsRepository
import ru.practicum.android.diploma.di.annotations.ApplicationScope

@Module
class DetailsDataModule {
    @ApplicationScope
    @Provides
     fun bindDetailsRepository(detailsRepositoryImpl: DetailsRepositoryImpl): DetailsRepository {
         return detailsRepositoryImpl
     }

    @Provides
    fun bindDLocalDataSource(localDataSource: LocalDataSourceDetailsImpl): LocalDataSource{
        return localDataSource
    }

    @Provides
    fun provideDao(database: FavoriteVacanciesDb): FavoriteDao{
        return database.getDao()
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
}
