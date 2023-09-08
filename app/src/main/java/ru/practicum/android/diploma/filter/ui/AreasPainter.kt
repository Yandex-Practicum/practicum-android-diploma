package ru.practicum.android.diploma.filter.ui

import android.view.View
import com.google.android.material.snackbar.Snackbar
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentAreasBinding
import ru.practicum.android.diploma.util.thisName


class AreasPainter {

    fun showLoadingScreen(binding: FragmentAreasBinding) {
        val fragment = binding.thisName
        val context = binding.root.context
        with(binding) {
            if (fragment == "RegionFragment") toolbar.title = context.getString(R.string.choose_region)
            else toolbar.title = context.getString(R.string.choose_country)
            placeholder.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
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

    fun showNoData(binding: FragmentAreasBinding, message: String) {
        showMessage(binding, message)
    }

    fun showOffline(binding: FragmentAreasBinding, message: String) {
        showMessage(binding, message)
    }

    fun showError(binding: FragmentAreasBinding, message: String) {
        showMessage(binding, message)
    }

}