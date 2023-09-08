package ru.practicum.android.diploma.filter.ui.models

import android.view.View
import com.google.android.material.snackbar.Snackbar
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentAreasBinding
import ru.practicum.android.diploma.util.thisName

sealed interface RenderState {

    fun render(binding: FragmentAreasBinding)

    object Loading : RenderState {
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

    data class NoData(val binding: FragmentAreasBinding, val message: String) : RenderState {
        override fun render(binding: FragmentAreasBinding) {
            super.showMessage(binding, message)
        }
    }

    data class Offline(val binding: FragmentAreasBinding, val message: String) : RenderState {
        override fun render(binding: FragmentAreasBinding) {
            super.showMessage(binding, message)
        }
    }

    data class Error(val binding: FragmentAreasBinding, val message: String) : RenderState {
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