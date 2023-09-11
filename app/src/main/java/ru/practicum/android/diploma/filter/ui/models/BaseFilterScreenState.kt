package ru.practicum.android.diploma.filter.ui.models

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBaseBinding
import ru.practicum.android.diploma.filter.domain.models.SelectedFilter


sealed interface BaseFilterScreenState {

    fun render(binding: FragmentFilterBaseBinding)

    object Empty : BaseFilterScreenState {
        override fun render(binding: FragmentFilterBaseBinding) {
            with(binding) {
                chooseBaseFilterBtn.visibility = View.GONE
                bottomContainerToApply.visibility = View.GONE
                amountText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
            }

        }
    }

    data class Content(private val selectedFilter: SelectedFilter) : BaseFilterScreenState {

        @SuppressLint("SetTextI18n")
        override fun render(binding: FragmentFilterBaseBinding) {
            binding.departmentText.setText(selectedFilter.industry?.name ?: "")
            binding.workPlaceText.setText(
                (selectedFilter.country?.name ?: "") +
                        "${
                            if (!selectedFilter.region?.name.isNullOrEmpty()) ", "
                            else {
                                ""
                            }
                        }${selectedFilter.region?.name ?: ""}"
            )
        }
    }


    object Choose : BaseFilterScreenState {
        override fun render(binding: FragmentFilterBaseBinding) {
            with(binding) {
                chooseBaseFilterBtn.visibility = View.VISIBLE
                bottomContainerToApply.visibility = View.GONE
                amountText.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    AppCompatResources.getDrawable(amountText.context, R.drawable.close_btn),
                    null
                )
            }
        }
    }

    object Apply : BaseFilterScreenState {
        override fun render(binding: FragmentFilterBaseBinding) {
            with(binding) {
                chooseBaseFilterBtn.visibility = View.GONE
                bottomContainerToApply.visibility = View.VISIBLE
                amountText.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    AppCompatResources.getDrawable(amountText.context, R.drawable.close_btn),
                    null
                )
            }
        }
    }
}