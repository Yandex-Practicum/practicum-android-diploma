package ru.practicum.android.diploma.ui.vacancydetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.api.DetailInteractor
import ru.practicum.android.diploma.domain.models.detail.VacancyDetail
import ru.practicum.android.diploma.ui.vacancydetail.DetailState

class DetailViewModel(
    val detailInteractor: DetailInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<DetailState>()
    fun observeState(): LiveData<DetailState> = stateLiveData

    fun searchDetailInformation(vacancyId: String) {
        renderState(DetailState.Loading)

        viewModelScope.launch {
            detailInteractor
                .searchDetailInformation(vacancyId)
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    private fun processResult(vacancyDetail: VacancyDetail?, errorMessage: Int?){
        when {
            errorMessage != null -> {
                renderState(
                    DetailState.Error(
                        errorMessage = R.string.server_error
                    )
                )
            }

            else -> {
                renderState(
                    DetailState.Content(
                        vacancyDetail = vacancyDetail!!
                    )
                )
            }
        }
    }

    fun renderState(detailState: DetailState){
        stateLiveData.postValue(detailState)
    }
}
