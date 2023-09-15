package ru.practicum.android.diploma.filter.ui.fragments.painters

import android.view.View
import com.google.android.material.snackbar.Snackbar
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentAreasBinding

class CountryPainter(private val binding: FragmentAreasBinding) {

    fun showLoadingScreen() {
        val context = binding.root.context
        with(binding) {
            toolbar.title = context.getString(R.string.choose_country)
            inputLayout.visibility = View.GONE
            applyBtn.visibility = View.GONE
            placeholder.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    fun showNoData(message: Int) {
        showMessage(message)
    }

    fun showOffline(message: Int) {
        showMessage(message)
    }

    fun showError(message: Int) {
        showMessage(message)
    }

    private fun showMessage(resId: Int) {
        val context = binding.root.context
        val message = context.getString(resId)
        Snackbar
            .make(context, binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(context.getColor(R.color.blue))
            .setTextColor(context.getColor(R.color.white))
            .show()
        binding.placeholder.visibility = View.VISIBLE
        binding.recycler.visibility = View.GONE
    }
}