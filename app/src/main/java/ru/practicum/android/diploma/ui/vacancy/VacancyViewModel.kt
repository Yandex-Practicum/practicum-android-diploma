package ru.practicum.android.diploma.ui.vacancy

import DetailsConverter
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.search.network.Resource
import ru.practicum.android.diploma.domain.api.CheckOnLikeRepository
import ru.practicum.android.diploma.domain.api.DeleteDataRepository
import ru.practicum.android.diploma.domain.api.SaveDataRepository
import ru.practicum.android.diploma.domain.models.DetailVacancy
import ru.practicum.android.diploma.domain.search.VacancyInteractor

class VacancyViewModel(
    val vacancyInteractor: VacancyInteractor,
    private val deleteVacancyRepository: DeleteDataRepository,
    private val saveVacancyRepository: SaveDataRepository,
    private val convertor: DetailsConverter,
    private val likeRepository: CheckOnLikeRepository
) : ViewModel() {

    private val _vacancyState = MutableLiveData<VacancyState>()
    val vacancyState: LiveData<VacancyState> = _vacancyState
    private var vacancy: DetailVacancy = DetailVacancy("","","","",false,"","", listOf(""),"","","","","","","",
        listOf(""),"","",0,false,0,"","","","","","")
    private var likeIndicator = MutableLiveData<Boolean>()
    private var likeJob: Job? = null

    private fun renderState(state: VacancyState) {
        _vacancyState.postValue(state)
    }
    fun getVacancyDetail(id: String) {
        if (id.isNotEmpty()) {
            renderState(VacancyState.Loading)
            viewModelScope.launch {
                vacancyInteractor
                    .getDetailVacancy(id)
                    .collect { resource ->
                        processResult(resource)
                    }
            }
        }
    }

    private fun processResult(resource: Resource<DetailVacancy>) {
        if (resource.data != null) {
            renderState(VacancyState.Content(resource.data))
        } else {
            renderState(VacancyState.Error)
        }
    }

    fun clickOnButton() {
        val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
            throwable.printStackTrace()
        }
        if (vacancyState.value is VacancyState.Content) {
            if (
                (vacancyState.value as VacancyState.Content)
                    .vacancy
                    .isFavorite
                    .isFavorite
            ) {
                _vacancyState.postValue(
                    (_vacancyState.value as VacancyState.Content).apply {
                        vacancy.isFavorite.isFavorite = false
                    }
                )
                viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
                    deleteVacancyRepository.delete((vacancyState.value as VacancyState.Content)
                        .vacancy.id)
                    Log.d("delete", "Deleted from fav")
                    //Log.d("deleted","${vacancyState.value as VacancyState.Content).vacancy.id}")
                }
            } else {
                _vacancyState.postValue(
                    (_vacancyState.value as VacancyState.Content).apply {
                        vacancy.isFavorite.isFavorite = true
                    }
                )
                viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
                    Log.d(
                        "DetailsConverterJob: ",
                        "${convertor.map((vacancyState.value as VacancyState.Content).vacancy)}"
                    )
                    saveVacancyRepository.save(
                        convertor.map((vacancyState.value as VacancyState.Content).vacancy)
                    )
                }
            }
        }
    }


    fun onLikedCheck(v: String): LiveData<Boolean> {
        likeJob = viewModelScope.launch {
            while (true) {
                delay(BUTTON_PRESSING_DELAY)
                v.let { id ->
                    likeRepository.favouritesCheck(id)
                        .collect { value ->
                            likeIndicator.postValue(value)
                        }
                }
            }
        }
        return likeIndicator
    }

    companion object {
        const val BUTTON_PRESSING_DELAY = 300L
    }
}
