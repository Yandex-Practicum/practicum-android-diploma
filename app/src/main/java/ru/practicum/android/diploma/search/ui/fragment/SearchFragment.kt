package ru.practicum.android.diploma.search.ui.fragment

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
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.search.ui.view_model.SearchViewModel
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {

    @Inject lateinit var searchAdapter: SearchAdapter
    private val viewModel: SearchViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    private val binding by viewBinding<FragmentSearchBinding>()
    
    override fun onResume() {
        super.onResume()
        viewModel.log(thisName, "onResume()")
//        TODO("Сделать запрос в SharedPrefs на наличие текущих филтров." +
//                "Далее если фильтры есть и строка поиска не пустая -> сделать запрос в сеть и обновить список" +
//            "Если фильтрые есть, но строка поиска пустая -> просто применить фильтр без запроса в сеть"
//                )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.log(thisName, "onViewCreated(view: View, savedInstanceState: Bundle?)")
    
        initListeners()
        initAdapter()
        initViewModelObserver()
        
    }
    
    private fun initViewModelObserver() {
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.uiState.collect { screenState -> screenState.render(binding) }
        }
    }
    
    private fun initListeners() {
        viewModel.log(thisName, "initListeners()")
        with(binding) {
            filterBtnToolbar.setOnClickListener {
                findNavController().navigate(
                    resId = R.id.action_searchFragment_to_filterBaseFragment
                )
            }
    
            searchEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.log(thisName, "$text")
                viewModel.onSearchQueryChanged(text.toString())
            }
        }
    }
    
    private fun initAdapter() {
        viewModel.log(thisName, "initAdapter()")
        (activity as RootActivity).component.inject(this)
    
        binding.recycler.adapter = searchAdapter
    
        searchAdapter.onClick = { vacancy -> navigateToDetails(vacancy) }
    }

    private fun navigateToDetails(vacancy: Vacancy) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToDetailsFragment(vacancy)
        )
    }
}