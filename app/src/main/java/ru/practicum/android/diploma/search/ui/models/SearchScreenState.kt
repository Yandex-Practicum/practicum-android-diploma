package ru.practicum.android.diploma.search.ui.models

import android.util.Log
import android.view.View
import com.google.android.material.appbar.AppBarLayout
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.ui.fragment.SearchAdapter
import ru.practicum.android.diploma.util.functional.Failure
import ru.practicum.android.diploma.util.thisName

sealed interface SearchScreenState {
    
    fun render(binding: FragmentSearchBinding)
    
    private fun refreshJobList(binding: FragmentSearchBinding, list: List<Vacancy>) {
        val adapter = (binding.recycler.adapter as SearchAdapter)
        adapter.list = list
        adapter.notifyDataSetChanged()
    }
    
    private fun isScrollingEnabled(binding: FragmentSearchBinding, isEnable: Boolean) {
        
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
    
    object Default : SearchScreenState {
        
        override fun render(binding: FragmentSearchBinding) {
            super.refreshJobList(binding, emptyList())
            super.isScrollingEnabled(binding, false)
            
            with(binding) {
                textFabSearch.visibility = View.GONE
                recycler.visibility = View.GONE
                placeholderImage.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
        
    }
    
    object Loading : SearchScreenState {
        
        override fun render(binding: FragmentSearchBinding) {
            super.refreshJobList(binding, emptyList())
            super.isScrollingEnabled(binding, false)
            
            with(binding) {
                val context = textFabSearch.context
                
                textFabSearch.text = context.getString(R.string.loading_message)
                textFabSearch.visibility = View.VISIBLE
                recycler.visibility = View.GONE
                placeholderImage.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
        }
        
    }

    data class Content(
        private val found: Int,
        private val jobList: List<Vacancy>
    ) : SearchScreenState {

        override fun render(binding: FragmentSearchBinding) {
            super.refreshJobList(binding, jobList)
            super.isScrollingEnabled(binding, true)

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
            }
        }
        
    }
    
    data class Error(
        val failure: Failure
    ) : SearchScreenState {
        
        override fun render(binding: FragmentSearchBinding) {
            super.refreshJobList(binding, emptyList())
            super.isScrollingEnabled(binding, false)
            Log.d(thisName, "render: $failure")
            when (failure) {
                is Failure.NetworkConnection -> showConnectionError(binding)
                is Failure.ServerError -> showEmpty(binding)
                is Failure.NotFound -> showEmpty(binding)
                is Failure.AppFailure -> showEmpty(binding)
                is Failure.UnknownError -> showEmpty(binding)
            }
        }
        
        private fun showConnectionError(binding: FragmentSearchBinding) {
            with(binding) {
                val context = textFabSearch.context
                textFabSearch.text = context.getString(R.string.update)
                textFabSearch.visibility = View.VISIBLE
                recycler.visibility = View.GONE
                placeholderImage.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
        }
        
        private fun showEmpty(binding: FragmentSearchBinding) {
            
            with(binding) {
                val context = textFabSearch.context
                textFabSearch.text = context.getString(R.string.empty_search_error)
                
                textFabSearch.visibility = View.VISIBLE
                recycler.visibility = View.GONE
                placeholderImage.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
    }
}