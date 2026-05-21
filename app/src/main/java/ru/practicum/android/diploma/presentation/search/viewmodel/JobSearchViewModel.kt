package ru.practicum.android.diploma.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.presentation.search.state.JobSearchState

class JobSearchViewModel : ViewModel() {
    private var isNextPageLoading = false
    private val _jobSearchState = MutableStateFlow<JobSearchState>(JobSearchState.Initial)
    val jobSearchState: StateFlow<JobSearchState> = _jobSearchState

    fun onLoadNextPage() {
        if (isNextPageLoading) {}
    }
}
