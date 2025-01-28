package ru.practicum.android.diploma.vacancy.presentation

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.vacancy.domain.api.VacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

class VacancyDetailsViewModel(
    private val vacancyId: String,
    private val interactor: VacancyDetailsInteractor,
) : ViewModel() {

    private val vacancyState = MutableLiveData<VacancyScreenState>()
    private var vacancy: Vacancy? = null

    fun getVacancyState(): LiveData<VacancyScreenState> = vacancyState

    init {
        loadVacancyDetails(vacancyId)
    }

    private fun loadVacancyDetails(vacancyId: String) {
        renderState(VacancyScreenState.LoadingState)

        viewModelScope.launch(Dispatchers.IO) {
            interactor.getVacancyDetails(vacancyId).collect { state ->
                when (state) {
                    is VacancyScreenState.ContentState -> {
                        vacancy = state.vacancy
                        renderState(state)
                    }
                    is VacancyScreenState.EmptyState -> {
                        renderState(VacancyScreenState.EmptyState)
                    }
                    is VacancyScreenState.NetworkErrorState -> {
                        renderState(VacancyScreenState.NetworkErrorState)
                    }

                    else -> {}
                }
            }
        }
    }

    private fun renderState(state: VacancyScreenState) {
        vacancyState.postValue(state)
    }

    fun share(context: Context) {
        val url = vacancy?.vacancyUrl
        if (url.isNullOrBlank()) {
            return
        }

        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        val share = Intent.createChooser(intent, null)
        share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(share)
    }

}
