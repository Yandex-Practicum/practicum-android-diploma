package ru.practicum.android.diploma.favorites.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.AppDatabase
import ru.practicum.android.diploma.common.util.Converter
import ru.practicum.android.diploma.favorites.domain.repository.FavoriteRepository

class FavoritesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val gson: Gson,
    private val sharedPrefs: SharedPreferences,
    private val converter: Converter,
    private val context: Context
) : FavoriteRepository
