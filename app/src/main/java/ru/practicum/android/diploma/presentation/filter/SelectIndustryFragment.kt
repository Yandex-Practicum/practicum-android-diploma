package ru.practicum.android.diploma.presentation.filter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.filter.Industry
import ru.practicum.android.diploma.presentation.ModelFragment
import ru.practicum.android.diploma.presentation.filter.chooseArea.state.IndustriesState
import ru.practicum.android.diploma.presentation.filter.selectArea.adaptor.IndystryAdapter

class SelectIndustryFragment : ModelFragment() {
    private val viewModel by viewModel<SelectIndustryViewModel>()
    private val listIndustry = mutableListOf<Industry>()
    private var industriesAdapter: IndystryAdapter? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarInclude.headerTitle.text = getString(R.string.select_industry)
        viewModel.observeIndustriesState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is IndustriesState.DisplayIndustries -> displayIndustries(state.industries)
                is IndustriesState.Error -> displayError(state.errorText)
            }
        }
        viewModel.observeFilteredIndustriesState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is IndustriesState.DisplayIndustries -> displayIndustries(state.industries)
                is IndustriesState.Error -> displayFilterError(state.errorText)
            }
        }
        setupSearchInput()
        viewModel.initScreen()
        viewModel.loadSelectedIndustry()
    }

    private fun setupSearchInput() {
        binding.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {}

            override fun afterTextChanged(editable: Editable?) {
                editable?.toString()?.let { query ->
                    viewModel.filterIndustries(query)
                }
            }
        })
    }


    private fun displayIndustries(industries: ArrayList<Industry>) {
        binding.apply {
            RecyclerView.visibility = View.VISIBLE
            placeholderMessage.visibility = View.GONE
        }
        if (industriesAdapter == null) {
            industriesAdapter = IndystryAdapter(listIndustry) { industry ->
                viewModel.onIndustryClicked(industry)
                val position = industries.indexOf(industry)
                industries[position] = industry.copy(isChecked = !industry.isChecked)
                viewModel.onIndustryClicked(industry)
                findNavController().popBackStack()
                industriesAdapter?.notifyItemChanged(position)
            }
            binding.RecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = industriesAdapter
            }
        }
        listIndustry.clear()
        listIndustry.addAll(industries)
        industriesAdapter!!.notifyDataSetChanged()
    }

    private fun displayError(errorText: String) {
        binding.apply {
            RecyclerView.isVisible = true
            placeholderMessage.isVisible = false
            placeholderMessageImage.setImageResource(R.drawable.search_placeholder_nothing_found)
            placeholderMessageText.text = errorText
        }
    }

    private fun displayFilterError(errorText: String) {
        binding.apply {
            RecyclerView.isVisible = false
            placeholderMessage.isVisible = true
            placeholderMessageImage.setImageResource(R.drawable.fitred_empty)
            placeholderMessageText.text = errorText
        }
    }
}