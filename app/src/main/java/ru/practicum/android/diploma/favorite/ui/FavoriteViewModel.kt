package ru.practicum.android.diploma.favorite.ui

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.Logger
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val logger: Logger) : ViewModel() {
    fun log(name: String, text: String){
        logger.log(name, text)
    }
}