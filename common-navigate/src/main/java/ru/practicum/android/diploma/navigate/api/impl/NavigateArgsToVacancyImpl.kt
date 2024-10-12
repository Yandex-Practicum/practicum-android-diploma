package ru.practicum.android.diploma.navigate.api.impl

import android.os.Bundle
import androidx.core.os.bundleOf
import ru.practicum.android.diploma.navigate.api.NavigateArgsToVacancy
import ru.practicum.android.diploma.navigate.state.NavigateEventState

private const val VACANCY_ID_NETWORK = "vacancy_instance"
private const val VACANCY_ID_DB = "vacancy_id"
private const val ARGS_STATE = "args_state"
private const val INPUT_NETWORK_STATE = 0
private const val INPUT_DB_STATE = 1
internal class NavigateArgsToVacancyImpl : NavigateArgsToVacancy<NavigateEventState> {

    override fun createArgs(state: NavigateEventState): Bundle {
        return when (state) {
            is NavigateEventState.ToVacancyDataSourceNetwork -> {
                bundleOf(
                    ARGS_STATE to INPUT_NETWORK_STATE,
                    VACANCY_ID_NETWORK to state.id
                )
            }
            is NavigateEventState.ToVacancyDataSourceDb -> {
                bundleOf(
                    ARGS_STATE to INPUT_DB_STATE,
                    VACANCY_ID_DB to state.id
                )
            }
            is NavigateEventState.ToFilter -> {
                bundleOf()
            }
        }
    }
}
