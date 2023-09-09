package ru.practicum.android.diploma.search.ui.fragment

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.appbar.AppBarLayout
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.functional.Failure
import ru.practicum.android.diploma.util.thisName

class SearchScreenPainter(
    private val binding: FragmentSearchBinding,
) {
    fun showDefault() {
        refreshJobList(emptyList())
        isScrollingEnabled(false)
        
        with(binding) {
            textFabSearch.visibility = View.GONE
            recycler.visibility = View.GONE
            placeholderImage.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            btnUpdate.visibility = View.GONE
        }
    }
    
    private fun refreshJobList(list: List<Vacancy>) {
        val adapter = (binding.recycler.adapter as SearchAdapter)
        adapter.list = list
        adapter.notifyDataSetChanged()
    }
    
    private fun isScrollingEnabled(isEnable: Boolean) {
        
        with(binding) {
            val toolbarLayoutParams: AppBarLayout.LayoutParams =
                searchToolbar.layoutParams as AppBarLayout.LayoutParams
            
            if (!isEnable) {
                toolbarLayoutParams.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
            } else {
                toolbarLayoutParams.scrollFlags =
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            }
            searchToolbar.layoutParams = toolbarLayoutParams
        }
    }
    
    fun showLoading() {
        refreshJobList(emptyList())
        isScrollingEnabled(false)
        
        with(binding) {
            val context = textFabSearch.context
            
            textFabSearch.text = context.getString(R.string.loading_message)
            textFabSearch.visibility = View.VISIBLE
            recycler.visibility = View.GONE
            placeholderImage.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            btnUpdate.visibility = View.GONE
        }
    }
    
    fun showContent(jobList: List<Vacancy>, found: Int) {
        refreshJobList(jobList)
        isScrollingEnabled(true)
        
        with(binding) {
            val context = textFabSearch.context
            
            val fabText = StringBuilder()
            fabText.append(context.getString(R.string.found))
            fabText.append(" ")
            fabText.append(
                context.resources.getQuantityString(
                    R.plurals.vacancies, found, found
                )
            )
            
            textFabSearch.text = fabText.toString()
            textFabSearch.visibility = View.VISIBLE
            recycler.visibility = View.VISIBLE
            placeholderImage.visibility = View.GONE
            progressBar.visibility = View.GONE
            btnUpdate.visibility = View.GONE
        }
    }
    
    fun renderError(failure: Failure) {
        refreshJobList(emptyList())
        isScrollingEnabled(false)
        Log.d(thisName, "render: $failure")
        
        when (failure) {
            is Failure.NetworkConnection -> showConnectionError()
            is Failure.ServerError -> showEmpty()
            is Failure.NotFound -> showEmpty()
            is Failure.AppFailure -> showEmpty()
        }
    }
    
    private fun showConnectionError() {
        with(binding) {
            val context = textFabSearch.context
            
            textFabSearch.text = context.getString(R.string.no_internet_message)
            textFabSearch.visibility = View.VISIBLE
            recycler.visibility = View.GONE
            placeholderImage.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            btnUpdate.visibility = View.VISIBLE
            
            hideKeyboard(ietSearch)
        }
    }
    
    private fun showEmpty() {
        
        with(binding) {
            val context = textFabSearch.context
            
            textFabSearch.text = context.getString(R.string.empty_search_error)
            textFabSearch.visibility = View.VISIBLE
            recycler.visibility = View.GONE
            placeholderImage.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            btnUpdate.visibility = View.GONE
            
        }
    }
    
    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
