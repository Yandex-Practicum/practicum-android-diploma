package ru.practicum.android.diploma.filter.ui.models

import android.view.View
import androidx.viewbinding.ViewBinding
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkPlaceFilterBinding

sealed interface PlaceUiState {

    fun render(binding: FragmentWorkPlaceFilterBinding)

    object Default : PlaceUiState {
        override fun render(binding: FragmentWorkPlaceFilterBinding) {
            //TODO("Not yet implemented")
        }
    }

    data class Content(
        val data: SelectedFilter
    ) : PlaceUiState {
        override fun render(binding: FragmentWorkPlaceFilterBinding) {
            with(binding) {
                val context = chooseBtn.context

                countyHint.visibility = View.VISIBLE
                countryText.text = data.country?.name ?: context.getString(R.string.region)
                countryCancelItem.visibility = View.VISIBLE
                countryItem.visibility = View.GONE
                regionText.text = data.region?.name ?: context.getString(R.string.region)
                countryItem.setImageResource(R.drawable.close_btn)
                binding.chooseBtn.visibility = View.VISIBLE
            }
        }
    }
}