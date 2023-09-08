package ru.practicum.android.diploma.filter.ui.models

import android.view.View
import com.google.android.material.snackbar.Snackbar
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentAreasBinding
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.ui.fragment.SearchAdapter
import ru.practicum.android.diploma.util.thisName

sealed interface AreasUiState {

    fun render(binding: FragmentAreasBinding)

    object Loading : AreasUiState {
        override fun render(binding: FragmentAreasBinding) {
            val fragment = binding.thisName
            val context = binding.root.context
            with(binding) {
                if (fragment == "RegionFragment") toolbar.title = context.getString(R.string.choose_region)
                else toolbar.title = context.getString(R.string.choose_country)
                placeholder.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
        }
    }

    data class Content<T>(val list: List<T>) : AreasUiState {
        override fun render(binding: FragmentAreasBinding) { /* ignore */ }
    }

    data class NoData(val message: String) : AreasUiState {
        override fun render(binding: FragmentAreasBinding) {
            super.showMessage(binding, message)
        }
    }

    data class Offline(val message: String) : AreasUiState {
        override fun render(binding: FragmentAreasBinding) {
            super.showMessage(binding, message)
        }
    }

    data class Error(val message: String) : AreasUiState {
        override fun render(binding: FragmentAreasBinding) {
            super.showMessage(binding, message)
        }
    }

    private fun showMessage(binding: FragmentAreasBinding, message: String) {
        val context = binding.root.context
        Snackbar
            .make(context, binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(context.getColor(R.color.blue))
            .setTextColor(context.getColor(R.color.white))
            .show()
        binding.placeholder.visibility = View.VISIBLE
        binding.recycler.visibility = View.GONE
    }
}