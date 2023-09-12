package ru.practicum.android.diploma.search.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.ui.fragment.adapter_delegate.LoadingAdapterDelegate
import ru.practicum.android.diploma.search.ui.fragment.adapter_delegate.MainCompositeAdapter
import ru.practicum.android.diploma.search.ui.fragment.adapter_delegate.VacancyAdapterDelegate
import ru.practicum.android.diploma.search.ui.models.LoadingItem
import ru.practicum.android.diploma.search.ui.models.SearchUiState
import ru.practicum.android.diploma.search.ui.view_model.SearchViewModel
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    private val binding by viewBinding<FragmentSearchBinding>()

    private val adapter by lazy {
        MainCompositeAdapter
            .Builder()
            .add(VacancyAdapterDelegate(onClick = { vacancy ->
                navigateToDetails(vacancy)
            }))
            .add(LoadingAdapterDelegate())
            .build()
    }
    
    private var isFilterSelected: Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.log(thisName, "++++onAttach()++++")
        (activity as RootActivity).component.inject(this)
    }

    override fun onResume() {
        super.onResume()
        viewModel.log(thisName, "++++onResume()++++")
        isFilterSelected = viewModel.isFilterSelected()
        viewModel.log(thisName, "isFilterSelected = $isFilterSelected")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.log(thisName, "++++onViewCreated()++++")
        isFilterSelected = viewModel.isFilterSelected()
        drawFilterBtn()
        initListeners()
        initAdapter()
        initViewModelObserver()
    }
    
    private fun drawFilterBtn() {
        viewModel.log(thisName, "++++drawFilterBtn++++ $isFilterSelected")
        with(binding) {
            if (isFilterSelected) filterBtnToolbar.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_filter_enable))
            else filterBtnToolbar.setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_filter))
        }
    }
    
    private fun initViewModelObserver() {
        viewModel.log(thisName, "++++initViewModelObserver()++++")
        with(viewModel) {
            viewLifecycleOwner.lifecycle.coroutineScope.launch {
                uiState.collect { screenState ->
                    val painter = SearchScreenPainter(binding)
                    when (screenState) {
                        is SearchUiState.Content -> {
                            if (screenState.isLastPage) adapter.submitList(screenState.list)
                            else adapter.submitList(screenState.list + LoadingItem)
                            painter.showContent(screenState.found)
                        }
                        is SearchUiState.Default -> { painter.showDefault() }
                        is SearchUiState.Error -> { painter.renderError(screenState.error) }
                        is SearchUiState.ErrorScrollLoading -> { painter.renderErrorScrolling(screenState.error) }
                        is SearchUiState.Loading -> { painter.showLoading() }
                    }
                }
            }
        }
    }

    private fun initListeners() {
        viewModel.log(thisName, "++++initListeners()++++")

        with(binding) {
            filterBtnToolbar.setOnClickListener {
                findNavController().navigate(
                    SearchFragmentDirections
                        .actionSearchFragmentToFilterBaseFragment(null,null,null)
                )
            }
            
            viewModel.log(thisName, "endIconDrawable = ${searchInputLayout.endIconDrawable}")
            
            searchInputLayout.isHintEnabled = false
            
            ietSearch.doOnTextChanged { text, _, _, _ ->
                viewModel.onSearchQueryChanged(text.toString())
                
                if (text.isNullOrEmpty()) {
                    searchInputLayout.endIconMode = TextInputLayout.END_ICON_NONE
                    searchInputLayout.endIconDrawable =
                        AppCompatResources.getDrawable(requireContext(), R.drawable.ic_search)
                } else {
                    searchInputLayout.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
                    searchInputLayout.endIconDrawable =
                        AppCompatResources.getDrawable(requireContext(), R.drawable.ic_clear)
                }
            }
            
            btnUpdate.setOnClickListener {
                viewModel.searchVacancies(
                    query = ietSearch.text.toString(),
                    isFirstPage = true
                )
            }
            
            recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    viewModel.log(thisName, "++++OnScrollListener()++++")
                    
                    if (dy > 0) {
                        val pos = (recycler.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                        val itemsCount = adapter.itemCount
                        if (pos >= itemsCount - 5) {
                            viewModel.onScrolledBottom()
                        }
                    }
                }
            })
        }
    }
    
    private fun initAdapter() {
        viewModel.log(thisName, "initAdapter()")
        binding.recycler.adapter = adapter
    }
    
    private fun navigateToDetails(vacancy: Vacancy) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailsFragment(vacancy.id)
        )
    }
}