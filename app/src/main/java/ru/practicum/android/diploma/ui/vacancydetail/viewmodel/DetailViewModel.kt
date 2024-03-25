package ru.practicum.android.diploma.ui.vacancydetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.api.DetailInteractor
import ru.practicum.android.diploma.domain.favorite.FavoriteInteractor
import ru.practicum.android.diploma.domain.models.detail.VacancyDetail
import ru.practicum.android.diploma.ui.vacancydetail.DetailState

class DetailViewModel(
    val detailInteractor: DetailInteractor,
    val interactorFavorite: FavoriteInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<DetailState>()
    fun observeState(): LiveData<DetailState> = stateLiveData

    fun searchDetailInformation(vacancyId: String) {
        renderState(DetailState.Loading)

        viewModelScope.launch {
            val stateDb = interactorFavorite.getAppIdVacancy()
            if (stateDb.contains(vacancyId)) {
                val itemFromDb = interactorFavorite.getVacancyId(vacancyId)
                renderState(DetailState.Content(itemFromDb))
            } else {
                detailInteractor
                    .searchDetailInformation(vacancyId)
                    .collect { pair ->
                        processResult(pair.first, pair.second)
                    }
            }
        }
    }

    private fun processResult(vacancyDetail: VacancyDetail?, errorMessage: Int?) {
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

    fun renderState(detailState: DetailState) {
        stateLiveData.postValue(detailState)
    }

    fun onFavoriteClicked() {
        stateLiveData.value?.getCurrentIfReady()?.let { detailState ->
            viewModelScope.launch {
                if (detailState.vacancyDetail.isFavorite) {
                    interactorFavorite.deleteVacancy(detailState.vacancyDetail.id.toInt())
                    stateLiveData.postValue(
                        DetailState.Content(
                            detailState.vacancyDetail.copy(isFavorite = false)
                        )
                    )
                } else {
                    interactorFavorite.addVacancy(detailState.vacancyDetail)
                    stateLiveData.postValue(
                        DetailState.Content(
                            detailState.vacancyDetail.copy(isFavorite = true)
                        )
                    )
                }
            }
        }
    }

    private fun DetailState.getCurrentIfReady(): DetailState.Content? =
        if (this is DetailState.Content) this else null
}
