package ru.practicum.android.diploma.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.practicum.android.diploma.details.ui.DetailsViewModel
import ru.practicum.android.diploma.di.annotations.ViewModelKey
import ru.practicum.android.diploma.favorite.ui.FavoriteViewModel
import ru.practicum.android.diploma.filter.ui.view_models.BaseFilterViewModel
import ru.practicum.android.diploma.filter.ui.view_models.CountryViewModel
import ru.practicum.android.diploma.filter.ui.view_models.RegionViewModel
import ru.practicum.android.diploma.filter.ui.view_models.WorkPlaceViewModel
import ru.practicum.android.diploma.root.RootViewModel
import ru.practicum.android.diploma.search.ui.view_model.SearchViewModel
import ru.practicum.android.diploma.similars.SimilarVacanciesViewModel

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


    @IntoMap
    @ViewModelKey(BaseFilterViewModel::class)
    @Binds
    fun bindBaseFilterViewModel(baseFilterViewModel: BaseFilterViewModel): ViewModel

    @IntoMap
    @ViewModelKey(CountryViewModel::class)
    @Binds
    fun bindCountryViewModel(baseFilterViewModel: BaseFilterViewModel): ViewModel

    @IntoMap
    @ViewModelKey(RegionViewModel::class)
    @Binds
    fun bindRegionViewModel(baseFilterViewModel: BaseFilterViewModel): ViewModel

    @IntoMap
    @ViewModelKey(WorkPlaceViewModel::class)
    @Binds
    fun bindWorkPlaceViewModel(baseFilterViewModel: BaseFilterViewModel): ViewModel

    @IntoMap
    @ViewModelKey(SimilarVacanciesViewModel::class)
    @Binds
    fun bindSimilarVacanciesViewModel(similarVacanciesViewModel: SimilarVacanciesViewModel): ViewModel
}