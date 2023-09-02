package ru.practicum.android.diploma.filters.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFilterSelectionBinding
import ru.practicum.android.diploma.filters.domain.models.Country
import ru.practicum.android.diploma.filters.domain.models.Industries
import ru.practicum.android.diploma.filters.domain.models.Region
import ru.practicum.android.diploma.filters.presentation.FiltersViewModel
import ru.practicum.android.diploma.filters.ui.adapter.FilterSelectionClickListener
import ru.practicum.android.diploma.filters.ui.adapter.FiltersAdapter
import ru.practicum.android.diploma.util.BindingFragment

class FragmentChooseFilter:BindingFragment<FragmentFilterSelectionBinding>() {

    private val viewModel by viewModel<FiltersViewModel>()
    private var adapter:FiltersAdapter? = null

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFilterSelectionBinding {
        return FragmentFilterSelectionBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        binding.recyclerViewFilters.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFilters.adapter = adapter


    }
    private fun initAdapter(){
        adapter = FiltersAdapter(object: FilterSelectionClickListener {
            override fun onClickRegion(model: Region?) {
            }
            override fun onClickIndustries(model: Industries?) {
            }
            override fun onClickCountry(model: Country?) {
            }
        })
    }
}