package ru.practicum.android.diploma.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import ru.practicum.android.diploma.root.RootViewModel

@Module
interface ViewModelModule {
    @IntoMap
    @StringKey("RootViewModel")
    @Binds
    fun bindRootViewModel(rootViewModel: RootViewModel): ViewModel
}