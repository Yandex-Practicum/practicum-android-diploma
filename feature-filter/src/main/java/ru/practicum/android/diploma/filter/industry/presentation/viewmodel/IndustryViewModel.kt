package ru.practicum.android.diploma.filter.industry.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.industry.domain.model.IndustryModel
import ru.practicum.android.diploma.filter.industry.domain.usecase.IndustryInteractor
import ru.practicum.android.diploma.filter.industry.presentation.viewmodel.state.IndustryState

internal class IndustryViewModel(
    private val industryInteractor: IndustryInteractor
) : ViewModel() {

    private var _industriesListLiveData = MutableLiveData<IndustryState>()
    val industriesListLiveData: LiveData<IndustryState> = _industriesListLiveData
    private var unfilteredList: MutableList<IndustryModel> = mutableListOf()

    init {
        getIndustriesList()
    }

    private fun getIndustriesList() {
        viewModelScope.launch(Dispatchers.IO) {
            industryInteractor.getIndustriesList().collect { response ->
                _industriesListLiveData.postValue(IndustryState.Loading)
                response.first?.let { listIndustry ->
                    if (listIndustry.isEmpty()) {
                        unfilteredList.clear()
                        _industriesListLiveData.postValue(IndustryState.Empty)
                    } else {
                        unfilteredList.addAll(listIndustry)
                        _industriesListLiveData.postValue(IndustryState.Content(listIndustry))
                    }
                } ?: {
                    unfilteredList.clear()
                    _industriesListLiveData.postValue(IndustryState.Empty)
                }
                response.second?.let {
                    unfilteredList.clear()
                    _industriesListLiveData.postValue(IndustryState.Error)
                }
            }
        }
    }

    fun filterList(query: String) {
        val newList = unfilteredList
        _industriesListLiveData.postValue(
            IndustryState.Content(
                newList.filter { item ->
                    item.name?.lowercase()?.contains(query.lowercase()) ?: false
                }
            )
        )
    }

    fun resetToFullList() {
        _industriesListLiveData.postValue(IndustryState.Content(unfilteredList))
    }

    private var _currentIndustryInDataFilterLiveData = MutableLiveData<IndustryModel?>()
    val currentIndustryInDataFilterLiveData: LiveData<IndustryModel?> = _currentIndustryInDataFilterLiveData

    fun updateProfessionInDataFilterBuffer(branchOfProfession: IndustryModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val success = industryInteractor.updateProfessionInDataFilterBuffer(branchOfProfession)
            if (success != -1) {
                _currentIndustryInDataFilterLiveData.postValue(branchOfProfession)
            }
        }
    }

    fun getBranchOfProfessionDataFilterBuffer() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentIndustryInDataFilterLiveData.postValue(
                industryInteractor.getBranchOfProfessionDataFilterBuffer()
            )
        }
    }
}
