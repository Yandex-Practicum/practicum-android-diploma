package ru.practicum.android.diploma.vacancy.presentation

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.util.network.HttpStatusCode.OK
import ru.practicum.android.diploma.vacancy.domain.api.GetVacancyDetailsInteractor
import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

class VacancyDetailsViewModel(
    private val vacancyId: String,
    private val interactor: GetVacancyDetailsInteractor
) : ViewModel() {

    private val vacancyState = MutableLiveData<VacancyScreenState>()

    fun getVacancyState(): LiveData<VacancyScreenState> = vacancyState

    init {
        loadVacancyDetails(vacancyId)
    }

    private fun loadVacancyDetails(vacancyId: String) {
        renderState(VacancyScreenState.LoadingState)
        viewModelScope.launch(Dispatchers.IO) {
            interactor.getVacancyDetails(vacancyId)
                .collect { result ->
                    when (result.second) {
                        null,
                        OK -> processSuccessResult(result.first)

                        else -> renderState(VacancyScreenState.NetworkErrorState)
                    }
                }
        }

    }

    private fun renderState(state: VacancyScreenState) {
        vacancyState.postValue(state)
    }

    private var vacancyUrl = ""
    private fun processSuccessResult(vacancy: Vacancy?) {
        if (vacancy == null) {
            Log.d("MyTag", "null")
            renderState(VacancyScreenState.EmptyState)
        } else {
            Log.d("MyTag", vacancy.toString())
            vacancyUrl = vacancy.vacancyUrl
            renderState(VacancyScreenState.ContentState(vacancy))
        }
    }

    fun share(context: Context) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, vacancyUrl)
            type = "text/plain"
        }
        val share = Intent.createChooser(intent, null)
        share.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(share)
    }
}
