package ru.practicum.android.diploma.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.practicum.android.diploma.root.ExampleUseCase
import ru.practicum.android.diploma.root.RootViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val exampleUseCase: ExampleUseCase
) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == RootViewModel::class.java){
            return RootViewModel(exampleUseCase) as T
        }
        throw RuntimeException("Unknown viewModel $modelClass")
    }
}