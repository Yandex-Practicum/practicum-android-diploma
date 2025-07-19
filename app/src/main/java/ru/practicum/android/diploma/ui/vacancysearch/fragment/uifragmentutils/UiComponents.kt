package ru.practicum.android.diploma.ui.vacancysearch.fragment.uifragmentutils

import android.app.Activity
import android.content.Context
import android.view.View
import ru.practicum.android.diploma.databinding.VacancySearchFragmentBinding
import ru.practicum.android.diploma.ui.vacancysearch.recyclerview.VacancyItemAdapter

data class UiComponents(
    val binding: VacancySearchFragmentBinding,
    val adapter: VacancyItemAdapter,
    val clearFocusView: View,
    val context: Context,
    val activity: Activity
)
