package ru.practicum.android.diploma.team.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.team.ui.TeamViewModel
import ru.practicum.android.diploma.team.ui.TeamViewModelImpl

val teamModule = module {
    viewModel<TeamViewModel> {
        TeamViewModelImpl()
    }
}
