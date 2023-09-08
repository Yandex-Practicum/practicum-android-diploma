package ru.practicum.android.diploma.filter.ui.fragments.painters

import android.view.View
import com.google.android.material.snackbar.Snackbar
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentAreasBinding
import ru.practicum.android.diploma.util.thisName


class CountryPainter(private val binding: FragmentAreasBinding) {

    fun showLoadingScreen() {
        val fragment = binding.thisName
        val context = binding.root.context
        with(binding) {
            if (fragment == "RegionFragment") toolbar.title = context.getString(R.string.choose_region)
            else toolbar.title = context.getString(R.string.choose_country)
            inputLayout.visibility = View.GONE
            applyBtn.visibility = View.GONE
            placeholder.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    fun showNoData(message: String) {
        showMessage(message)
    }

    fun showOffline(message: String) {
        showMessage(message)
    }

    fun showError(message: String) {
        showMessage(message)
    }

    private fun showMessage(message: String) {
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