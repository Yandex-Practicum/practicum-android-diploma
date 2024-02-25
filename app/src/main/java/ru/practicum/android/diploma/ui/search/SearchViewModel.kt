package ru.practicum.android.diploma.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.Constant.STATIC_PAGE_SIZE
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.ui.search.adapter.SearchPage

class SearchViewModel(private val searchInteractor: SearchInteractor) : ViewModel() {
    val actionStateFlow = MutableSharedFlow<String>()

    val stateVacancyData = actionStateFlow.flatMapLatest {
        getPagingData(it)
    }

    fun getPagingData(search: String): StateFlow<PagingData<Vacancy>> {
        return Pager(PagingConfig(pageSize = STATIC_PAGE_SIZE)) {
            SearchPage(search, searchInteractor)
        }.flow.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    }

    fun search(vacancy: String) {
        viewModelScope.launch(Dispatchers.IO) {
            actionStateFlow.emit(vacancy)
        }
    }
}
