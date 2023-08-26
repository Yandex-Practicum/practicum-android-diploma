package ru.practicum.android.diploma.di

import dagger.BindsInstance
import dagger.Subcomponent
import ru.practicum.android.diploma.di.annotations.IdQualifier
import ru.practicum.android.diploma.di.modules.ViewModelModule
import ru.practicum.android.diploma.root.RootActivity

@Subcomponent(modules = [ViewModelModule::class])
interface ActivityComponent {
    fun inject(rootActivity: RootActivity)
    @Subcomponent.Factory
    interface Factory{
        fun create(
            @BindsInstance @IdQualifier id: Int
        ): ActivityComponent
    }
}