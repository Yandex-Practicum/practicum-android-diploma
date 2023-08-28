package ru.practicum.android.diploma.search.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.search.ui.models.SearchScreenState
import ru.practicum.android.diploma.search.ui.view_model.SearchViewModel
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {

    @Inject @JvmField var searchAdapter: SearchAdapter? = null
    private val viewModel: SearchViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    private val binding by viewBinding<FragmentSearchBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.log(thisName, "onViewCreated   $viewModel")
    
        initViewModelObserver()
        initListeners()
        initAdapter()
    
    
        searchAdapter?.onClick = { vacancy ->
            viewModel.log(thisName, "onClickWithDebounce $vacancy")
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        searchAdapter = null
    }
    
    private fun initViewModelObserver() {
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.contentState.collect { screenState ->
                render(screenState)
            }
        }
    }
    
    private fun render(screenState: SearchScreenState) {
        when (screenState) {
            SearchScreenState.Empty -> TODO()
            SearchScreenState.Loading -> TODO()
            is SearchScreenState.Content -> TODO()
            is SearchScreenState.Error -> TODO()
        }
    }
    
    private fun initListeners() {
        with(binding) {
            filterBtnToolbar.setOnClickListener {
                findNavController().navigate(
                    R.id.action_searchFragment_to_filterBaseFragment
                )
            }
        }
    }
    
    private fun initAdapter() {
        TODO("Not yet implemented")
    }
}