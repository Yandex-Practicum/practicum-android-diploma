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
    private var selectedIndustry: Industry? = null
    private var selectedIndex: Int? = null

    fun observeState(): LiveData<BranchScreenState> = stateLiveData

    fun getBranches(searchText: String, selectedIndustry: Industry?) {
        viewModelScope.launch(Dispatchers.IO) {
            getIndustryByTextUseCase.execute(searchText).collect {
                when (it) {
                    is Result.Success -> {
                        branches.clear()
                        branches.addAll(it.data)
                        updateSelectedIndex(selectedIndustry)
                        renderState(
                            BranchScreenState.Content(
                                branches,
                                this@BranchViewModel.selectedIndustry,
                                selectedIndex
                            )
                        )
                    }

                    is Result.Error -> renderState(BranchScreenState.Error)
                }
            }
        }
    }

    private fun updateSelectedIndex(selectedIndustry: Industry?) {
        if (selectedIndustry != null) {
            for (i in branches.indices) {
                if (branches[i].id == selectedIndustry.id) {
                    this@BranchViewModel.selectedIndustry = selectedIndustry
                    selectedIndex = i
                    break
                }
            }
        }
    }

    fun onSelectIndustryEvent(industry: Industry, index: Int) {
        selectedIndustry = industry
        selectedIndex = index
        renderState(BranchScreenState.Content(branches, selectedIndustry, selectedIndex))
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
