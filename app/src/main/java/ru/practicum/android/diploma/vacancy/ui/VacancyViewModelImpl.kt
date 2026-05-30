package ru.practicum.android.diploma.vacancy.ui

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.data.network.ResultCode
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.favorites.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.vacancy.domain.api.VacancyInteractor
import ru.practicum.android.diploma.vacancy.domain.models.VacancyDetails

class VacancyViewModelImpl(
    private val id: String,
    private val vacancyInteractor: VacancyInteractor,
    private val favoritesInteractor: FavoritesInteractor,
) : VacancyViewModel() {

    private val loadResult = MutableStateFlow<LoadResult>(LoadResult.Loading)

    override val state: StateFlow<VacancyState> = combine(
        loadResult,
        favoritesInteractor.isFavorite(id),
    ) { result, isFav -> map(result, isFav) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, VacancyState.Loading)

    init {
        load()
    }

    override fun onFavoriteClicked() {
        val current = state.value as? VacancyState.Content ?: return
        viewModelScope.launch {
            if (current.isFavorite) {
                favoritesInteractor.remove(id)
            } else {
                favoritesInteractor.add(current.details)
            }
        }
    }

    private fun load() {
        viewModelScope.launch {
            loadResult.value = LoadResult.Loading
            when (val result = vacancyInteractor.getById(id)) {
                is Resource.Success -> {
                    loadResult.value = LoadResult.Loaded(result.data, fromCache = false)
                }
                is Resource.Error -> handleError(result.code)
                Resource.Loading -> Unit
            }
        }
    }

    private suspend fun handleError(code: Int?) {
        val cached = if (favoritesInteractor.isFavorite(id).first()) {
            favoritesInteractor.getById(id).first()
        } else {
            null
        }
        loadResult.value = when {
            cached != null -> LoadResult.Loaded(cached, fromCache = true)
            code == ResultCode.NO_INTERNET -> LoadResult.NoInternet
            else -> LoadResult.Failed
        }
    }

    private fun map(result: LoadResult, isFavorite: Boolean): VacancyState = when (result) {
        LoadResult.Loading -> VacancyState.Loading
        is LoadResult.Loaded -> VacancyState.Content(
            details = result.details,
            isFavorite = isFavorite,
            fromCache = result.fromCache,
        )
        LoadResult.NoInternet -> VacancyState.NoInternet
        LoadResult.Failed -> VacancyState.Error
    }

    private sealed interface LoadResult {
        object Loading : LoadResult
        data class Loaded(val details: VacancyDetails, val fromCache: Boolean) : LoadResult
        object NoInternet : LoadResult
        object Failed : LoadResult
    }
}
