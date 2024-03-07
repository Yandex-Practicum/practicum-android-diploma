package ru.practicum.android.diploma.filter.industry.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favourites.presentation.CLICK_DEBOUNCE_DELAY
import ru.practicum.android.diploma.filter.industry.domain.model.Industry
import ru.practicum.android.diploma.filter.industry.domain.usecase.GetIndustriesByTextUseCase
import ru.practicum.android.diploma.util.Result

class BranchViewModel(private val getIndustryByTextUseCase: GetIndustriesByTextUseCase) : ViewModel() {

    private val stateLiveData = MutableLiveData<BranchScreenState>()
    private var isClickAllowed = true
    private val branches: ArrayList<Industry> = arrayListOf()

    fun observeState(): LiveData<BranchScreenState> = stateLiveData

    fun getBranches(searchText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getIndustryByTextUseCase.execute(searchText).collect {
                when (it) {
                    is Result.Success -> {
                        branches.clear()
                        branches.addAll(it.data!!)
                        renderState(BranchScreenState.Content(branches))
                    }
                    is Result.Error -> renderState(BranchScreenState.Error)
                }
            }
        }
    }

    private fun renderState(branchScreenState: BranchScreenState) {
        stateLiveData.postValue(branchScreenState)
    }

    fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewModelScope.launch(Dispatchers.IO) {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }
}
