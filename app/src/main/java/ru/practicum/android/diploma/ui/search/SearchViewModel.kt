package ru.practicum.android.diploma.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.data.Constant.STATIC_PAGE_SIZE
import ru.practicum.android.diploma.data.search.network.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyData
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.ui.search.adapter.SearchPage
import ru.practicum.android.diploma.util.UtilsDebounce
import java.net.ConnectException

class SearchViewModel(private val searchInteractor: SearchInteractor) : ViewModel() {
    val searchState = MutableStateFlow<SearchState>(SearchState.Start)
    var errorMessage = MutableLiveData<String?>()

    var job: Job? = null
    val actionStateFlow = MutableSharedFlow<String>()
    var stateRefresh: LoadState? = null


    val stateVacancyData = actionStateFlow.flatMapLatest {
        getPagingData(it)
    }

    suspend fun search(
        expression: String,
        page: Int,
    ): Resource<VacancyData> {
        val result = searchInteractor.search(expression, page)
        SearchState.Loaded.counter = result.data?.found ?: 0
        return result
    }

    fun getPagingData(search: String): StateFlow<PagingData<Vacancy>> {
        return Pager(PagingConfig(pageSize = STATIC_PAGE_SIZE)) {
            SearchPage(search, ::search)
        }.flow.map {
            it
        }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    }

    fun search(vacancy: String) {
        if (vacancy.isNotEmpty()) {
            searchState.value = SearchState.Search
            job = UtilsDebounce.debounce(viewModelScope, job, function = {
                searchState.value = SearchState.Loading
                viewModelScope.launch(Dispatchers.IO) {
                    actionStateFlow.emit(vacancy)
                }
            })
        } else {
            searchState.value = SearchState.Start
            job?.cancel()
        }
    }


    fun listener(loadState: CombinedLoadStates) {
        when (val refresh = loadState.source.refresh) {
            is LoadState.Error -> when (refresh.error) {
                is ConnectException -> searchState.value = SearchState.NoInternet
                is NullPointerException -> searchState.value = SearchState.FailedToGetList
            }

            LoadState.Loading -> {}
            is LoadState.NotLoading -> {
                if (stateRefresh == LoadState.Loading) {
                    searchState.value = SearchState.Loaded
                }
            }
        }
        stateRefresh = loadState.source.refresh

        val errorState = when {
            loadState.source.append is LoadState.Error -> loadState.append as LoadState.Error
            loadState.source.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            else -> null
        }
        when (errorState?.error) {
            is ConnectException -> errorMessage.value = "Нет интернета"
        }
    }

    fun clearMessageAddPlayList() {
        errorMessage.value = null
    }
}
