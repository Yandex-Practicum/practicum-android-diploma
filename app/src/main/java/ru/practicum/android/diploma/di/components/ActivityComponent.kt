package ru.practicum.android.diploma.di.components

import dagger.Subcomponent
import ru.practicum.android.diploma.di.modules.ViewModelModule
import ru.practicum.android.diploma.favorite.ui.FavoriteFragment
import ru.practicum.android.diploma.filter.ui.fragments.AreasFragment
import ru.practicum.android.diploma.filter.ui.fragments.CountryFilterFragment
import ru.practicum.android.diploma.filter.ui.fragments.RegionFragment
import ru.practicum.android.diploma.filter.ui.fragments.WorkPlaceFilterFragment
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.search.ui.fragment.SearchFragment
import ru.practicum.android.diploma.similars.SimilarVacanciesFragment
import ru.practicum.android.diploma.team.ui.TeamFragment

@Subcomponent(modules = [ViewModelModule::class])
interface ActivityComponent {
    fun inject(rootActivity: RootActivity)
    fun inject(favoriteFragment: FavoriteFragment)
    fun inject(searchFragment: SearchFragment)
    fun inject(similarVacanciesFragment: SimilarVacanciesFragment)
    fun inject(teamFragment: TeamFragment)

    //========Filter================================================================================
    fun inject(areasFragment: AreasFragment)
    fun inject(workPlaceFilterFragment: WorkPlaceFilterFragment)
    fun inject(countryFilterFragment: CountryFilterFragment)
    fun inject(regionFragment: RegionFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create( /*@BindsInstance @IdQualifier id: Int*/): ActivityComponent
    }
}