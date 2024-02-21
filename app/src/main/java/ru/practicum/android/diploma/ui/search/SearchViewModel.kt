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
import ru.practicum.android.diploma.data.network.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.search.adapter.SearchPage
import java.util.UUID

class SearchViewModel : ViewModel() {
    val actionStateFlow = MutableSharedFlow<String>()

    val stateVacancyData = actionStateFlow.flatMapLatest {
        getPagingData(it)
    }

    fun getPagingData(search: String): StateFlow<PagingData<Vacancy>> {
        return Pager(PagingConfig(pageSize = 20)) {
            SearchPage(
                { it1, it2 ->
                    fakeData(it1, it2)
                },
                search
            )
        }
            .flow
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    }

    fun search(vacancy: String) {
        viewModelScope.launch(Dispatchers.IO) {
            actionStateFlow.emit(vacancy)
        }
    }

    suspend fun fakeData(expression: String, page: Int): Resource<List<Vacancy>> {
        val list = mutableListOf<Vacancy>()
        for (i in 0 until 20) {
            list.add(
                Vacancy(
                    id = UUID.randomUUID().toString(),
                    name = "$expression Станица:$page Номер:$i",
                    null, null, null, null, null, null,
                )
            )
        }
        return Resource(data = list)
    }
}
