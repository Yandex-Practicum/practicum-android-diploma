package ru.practicum.android.diploma.ui.favourite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.DeleteDataRepository
import ru.practicum.android.diploma.domain.api.GetDataRepository
import ru.practicum.android.diploma.presentation.favourite.FavouritesState
import java.sql.SQLException

class FavoriteViewModel(
    private val favoritesVacancyListRepository: GetDataRepository,
    private val deleteVacancyRepository: DeleteDataRepository,
) : ViewModel() {

    private val screenStatement: MutableLiveData<FavouritesState> = MutableLiveData()
    val screenState: LiveData<FavouritesState> get() = screenStatement

    // Получение данных
    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favoritesVacancyListRepository.get().collect { list ->
                    when {
                        list == null -> screenStatement.postValue(FavouritesState.DbError)
                        list.isEmpty() -> screenStatement.postValue(FavouritesState.Empty)
                        else -> {
                            screenStatement.postValue(FavouritesState.Content(list))
                            Log.d("got values from db", "$list")
                        }
                    }
                }
            } catch (e: SQLException) {
                screenStatement.postValue(FavouritesState.DbError)
                Log.e("Tag", e.stackTraceToString())
            }
        }
    }

    fun deleteVacancyFromFavorite(vacancyId: String) {
        viewModelScope.launch {
            deleteVacancyRepository.delete(vacancyId)
        }
    }
}
