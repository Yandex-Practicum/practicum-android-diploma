package ru.practicum.android.diploma.search.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.search.domain.models.NetworkError
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.ui.models.SearchScreenState
import ru.practicum.android.diploma.search.ui.view_model.SearchViewModel
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {

    @Inject @JvmField var searchAdapter: SearchAdapter? = null
    private val viewModel: SearchViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    private val binding by viewBinding<FragmentSearchBinding>()
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as RootActivity).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.log(thisName, "onViewCreated   $viewModel")
    
        initViewModelObserver()
        initListeners()
        initAdapter()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        searchAdapter = null
    }
    
    private fun initViewModelObserver() {
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.uiState.collect { screenState -> render(screenState) }
        }
    }
    
    private fun initListeners() {
        with(binding) {
            filterBtnToolbar.setOnClickListener {
                findNavController().navigate(
                    resId = R.id.action_searchFragment_to_filterBaseFragment
                )
            }
    
            searchEditText.doOnTextChanged { text, _,_,_ ->
                viewModel.onSearchQueryChanged(text.toString())
            }
        }
    }
    
    private fun initAdapter() {
        binding.recycler.adapter = searchAdapter
    
        searchAdapter?.onClick = { vacancy ->
            viewModel.log(thisName, "onClickWithDebounce $vacancy")
            findNavController().navigate(
                resId = R.id.action_searchFragment_to_filterBaseFragment,
                //args = bundleOf("KEY_DETAILS" to vacancy)
            )
        }
    }
    
    private fun render(screenState: SearchScreenState) {
        when (screenState) {
            is SearchScreenState.Default -> showDefault()
            is SearchScreenState.Loading -> showLoading()
            is SearchScreenState.Content -> showContent(screenState.jobList)
            is SearchScreenState.Error -> showError(screenState.error)
        }
    }
    
    private fun showDefault() {
        
        refreshJobList(emptyList())
        isScrollingEnabled(false)
        
        with(binding) {
            textFabSearch.visibility = View.GONE
            recycler.visibility = View.GONE
            placeholderImage.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }
    
    private fun showError(error: NetworkError) {
        
        when (error) {
            NetworkError.SEARCH_ERROR -> showEmpty()
            NetworkError.CONNECTION_ERROR -> showConnectionError()
        }
    }
    
    private fun showConnectionError() {
        refreshJobList(emptyList())
        isScrollingEnabled(false)
        
        with(binding) {
            
            textFabSearch.text = getString(R.string.update)
            
            textFabSearch.setOnClickListener {
            //viewModel.loadJobList(searchEditText.text.toString())
                textFabSearch.setOnClickListener(null)
            }
            
            textFabSearch.visibility = View.VISIBLE
            recycler.visibility = View.GONE
            placeholderImage.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }
    
    private fun showContent(jobList: List<Vacancy>) {
        refreshJobList(jobList)
        isScrollingEnabled(true)
        
        with(binding) {
            
            textFabSearch.text = getString(R.string.loading_message)
            
            textFabSearch.visibility = View.VISIBLE
            recycler.visibility = View.VISIBLE
            placeholderImage.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
    }
    
    private fun showLoading() {
        refreshJobList(emptyList())
        isScrollingEnabled(false)
        
        with(binding) {
            
            textFabSearch.text = getString(R.string.loading_message)
            
            textFabSearch.visibility = View.VISIBLE
            recycler.visibility = View.GONE
            placeholderImage.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }
    
    private fun showEmpty() {
        
        refreshJobList(emptyList())
        isScrollingEnabled(false)
        
        with(binding) {
            
            textFabSearch.text = getString(R.string.empty_search_error)
            
            textFabSearch.visibility = View.VISIBLE
            recycler.visibility = View.GONE
            placeholderImage.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }
    
    private fun refreshJobList(list: List<Vacancy>) {
        searchAdapter?.list = list
        searchAdapter?.notifyDataSetChanged()
    }
    
    private fun isScrollingEnabled(flag: Boolean) {
        binding.searchAppBarLayout.isNestedScrollingEnabled = flag
    }
}