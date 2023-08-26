package ru.practicum.android.diploma.di.modules

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.practicum.android.diploma.di.annotations.ViewModelKey
import ru.practicum.android.diploma.root.RootViewModel

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(RootViewModel::class)
    @Binds
    fun bindRootViewModel(rootViewModel: RootViewModel): ViewModel
}