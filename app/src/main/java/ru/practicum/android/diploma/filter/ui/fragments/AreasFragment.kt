package ru.practicum.android.diploma.filter.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentAreasBinding
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.ui.fragments.adapters.FilterAdapter
import ru.practicum.android.diploma.filter.ui.models.AreasUiState
import ru.practicum.android.diploma.filter.ui.models.AreasUiState.*
import ru.practicum.android.diploma.filter.ui.models.FilterScreenState
import ru.practicum.android.diploma.filter.ui.view_models.AreasViewModel
import ru.practicum.android.diploma.root.Debouncer
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding
import javax.inject.Inject

open class AreasFragment : Fragment(R.layout.fragment_areas) {

    protected open val fragment = ""
    @Inject lateinit var debouncer: Debouncer
    @Inject lateinit var filterAdapter: FilterAdapter
    protected val binding by viewBinding<FragmentAreasBinding>()
    protected open val viewModel: AreasViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as RootActivity).component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initAdapter()
        viewModel.getData()

        viewLifecycleOwner.lifecycle.coroutineScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                viewModel.log("CountryFilterFragment", "state ${state.thisName}")
                when (state) {
                    is Loading    -> state.render(binding)
                    is Content<*> -> renderContent(state.list)
                    is NoData     -> state.render(binding)
                    is Offline    -> state.render(binding)
                    is Error      -> state.render(binding)
                }
            }
        }
    }

    protected open fun initListeners() {
        //TODO("Not yet implemented")
    }

    protected open fun initAdapter() {
        //TODO("Not yet implemented")
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun renderContent(list: List<Any?>) {
        viewModel.log("CountryFragment", "renderContent: list.size = ${list.size})")
        binding.placeholder.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        filterAdapter.countryList = list as List<Country>
        filterAdapter.notifyItemRangeChanged(0, filterAdapter.itemCount)
    }

}