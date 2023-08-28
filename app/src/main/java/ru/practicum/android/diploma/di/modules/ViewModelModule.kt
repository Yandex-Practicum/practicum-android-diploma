package ru.practicum.android.diploma.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.practicum.android.diploma.details.ui.DetailsViewModel
import ru.practicum.android.diploma.di.annotations.ViewModelKey
import ru.practicum.android.diploma.favorite.domain.FavoriteViewModel
import ru.practicum.android.diploma.root.RootViewModel
import ru.practicum.android.diploma.search.ui.SearchViewModel

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(RootViewModel::class)
    @Binds
    fun bindRootViewModel(rootViewModel: RootViewModel): ViewModel

    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    @Binds
    fun bindFavoriteViewModel(favoriteViewModel: FavoriteViewModel): ViewModel

    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    @Binds
    fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    @Binds
    fun bindDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModel

}