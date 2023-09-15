package ru.practicum.android.diploma.similars

import android.view.View
import android.widget.Toast
import com.google.android.material.appbar.AppBarLayout
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSimilarsVacancyBinding
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.ui.fragment.adapter_delegate.MainCompositeAdapter

class SimilarVacanciesScreenPainter(
    private val binding: FragmentSimilarsVacancyBinding,
) {
    
    private val context = binding.root.context
    
    private fun isScrollingEnabled(isEnable: Boolean) {
        
        with(binding) {
            val toolbarLayoutParams: AppBarLayout.LayoutParams =
                similarVacancyToolbar.layoutParams as AppBarLayout.LayoutParams
            
            if (!isEnable) {
                toolbarLayoutParams.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
            } else {
                toolbarLayoutParams.scrollFlags =
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            }

            similarVacancyToolbar.layoutParams = toolbarLayoutParams
        }
    }

    fun showEmpty() {
        isScrollingEnabled(false)
        binding.iwAnim.visibility = View.GONE
        binding.recycler.visibility = View.GONE
        binding.placeHolder.visibility = View.VISIBLE
    }

    fun showContent(list: List<Vacancy>) {
        isScrollingEnabled(true)
        binding.placeHolder.visibility = View.GONE
        binding.iwAnim.visibility = View.GONE
        binding.recycler.visibility = View.VISIBLE
        val adapter = (binding.recycler.adapter as MainCompositeAdapter)
        adapter.submitList(list)
    }

    fun showOffline() {
        isScrollingEnabled(false)
        binding.placeHolder.text = context.getString(R.string.no_internet_message)
        binding.placeHolder.visibility = View.VISIBLE
        binding.iwAnim.visibility = View.GONE
        Toast.makeText(
            binding.root.context,
            context.getString(R.string.no_internet_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    fun showError() {
        isScrollingEnabled(false)
        binding.iwAnim.visibility = View.GONE
        binding.placeHolder.visibility = View.VISIBLE
        binding.placeHolder.text = context.getString(R.string.server_error)
        Toast.makeText(
            binding.root.context,
            context.getString(R.string.server_error),
            Toast.LENGTH_SHORT
        ).show()
    }

    fun showLoading() {
        isScrollingEnabled(false)
        binding.iwAnim.visibility = View.VISIBLE
        binding.placeHolder.visibility = View.GONE
    }
}