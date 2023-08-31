package ru.practicum.android.diploma.search.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
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
    
    override fun onResume() {
        viewModel.log(thisName, "onResume  $viewModel")
        super.onResume()
//        TODO("Сделать запрос в SharedPrefs на наличие текущих филтров." +
//                "Далее если фильтры есть и строка поиска не пустая -> сделать запрос в сеть и обновить список" +
//            "Если фильтрые есть, но строка поиска пустая -> просто применить фильтр без запроса в сеть"
//                )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.log(thisName, "onViewCreated   $viewModel")
    
        initListeners()
        initAdapter()
        initViewModelObserver()
        
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.log(thisName, "onDestroyView $viewModel")
        searchAdapter = null
    }
    
    private fun initViewModelObserver() {
        viewModel.log(thisName, "initViewModelObserver $viewModel")
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.uiState.collect { screenState -> render(screenState) }
        }
    }
    
    private fun initListeners() {
        viewModel.log(thisName, "initListeners $viewModel")
        with(binding) {
            filterBtnToolbar.setOnClickListener {
                findNavController().navigate(
                    resId = R.id.action_searchFragment_to_filterBaseFragment
                )
            }
    
            searchEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.onSearchQueryChanged(text.toString())
            }
        }
    }
    
    private fun initAdapter() {
        viewModel.log(thisName, "initAdapter $viewModel")
        
        (activity as RootActivity).component.inject(this)
        binding.recycler.adapter = searchAdapter
    
        searchAdapter?.onClick = { vacancy ->
            viewModel.log(thisName, "onClickWithDebounce $vacancy")
            navigateToDetails(vacancy)
        }
    }

    private fun navigateToDetails(vacancy: Vacancy) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailsFragment(vacancy)
        )
    }

    private fun render(screenState: SearchScreenState) {
        viewModel.log(thisName, "render -> ${screenState}")
        when (screenState) {
            is SearchScreenState.Default -> showDefault()
            is SearchScreenState.Loading -> showLoading()
            is SearchScreenState.Content -> showContent(screenState.jobList)
            is SearchScreenState.Error -> showError(screenState.error)
        }
    }
    
    private fun showDefault() {
        viewModel.log(thisName, "showDefault $viewModel")
        
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
        viewModel.log(thisName, "showError -> $error")
        when (error) {
            NetworkError.SEARCH_ERROR -> showEmpty()
            NetworkError.CONNECTION_ERROR -> showConnectionError()
        }
    }
    
    private fun showConnectionError() {
        viewModel.log(thisName, "showConnectionError $viewModel")
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
        viewModel.log(thisName, "showContent -> ${jobList.size}")
        refreshJobList(jobList)
        isScrollingEnabled(true)
        
        with(binding) {
            
            val fabText = StringBuilder()
            fabText.append(getString(R.string.found))
            fabText.append(" ")
            fabText.append(resources.getQuantityString(R.plurals.vacancies, jobList.size, jobList.size))
            
            textFabSearch.text = fabText.toString()
            
            textFabSearch.visibility = View.VISIBLE
            recycler.visibility = View.VISIBLE
            placeholderImage.visibility = View.GONE
            progressBar.visibility = View.GONE
        }
    }
    
    private fun showLoading() {
        viewModel.log(thisName, "showLoading $viewModel")
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
        viewModel.log(thisName, "showEmpty $viewModel")
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
        viewModel.log(thisName, "refreshJobList -> ${list.size}")
        searchAdapter?.list = list
        searchAdapter?.notifyDataSetChanged()
    }
    
    private fun isScrollingEnabled(isEnable: Boolean) {
        viewModel.log(thisName, "isScrollingEnabled -> $isEnable")
        
        with(binding) {
    
            val searchLayoutParams: AppBarLayout.LayoutParams =
                searchContainer.layoutParams as AppBarLayout.LayoutParams
            val toolbarLayoutParams: AppBarLayout.LayoutParams =
                searchToolbar.layoutParams as AppBarLayout.LayoutParams
            val textLayoutParams: AppBarLayout.LayoutParams =
                textFabSearch.layoutParams as AppBarLayout.LayoutParams
    
            if (!isEnable) {
                searchLayoutParams.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
                toolbarLayoutParams.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
                textLayoutParams.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
            } else {
                searchLayoutParams.scrollFlags =
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                toolbarLayoutParams.scrollFlags =
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                textLayoutParams.scrollFlags =
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            }
    
            searchContainer.layoutParams = searchLayoutParams
            searchToolbar.layoutParams = toolbarLayoutParams
            textFabSearch.layoutParams = textLayoutParams
        }
    }
}