package ru.practicum.android.diploma.filter.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.filter.ui.view_models.RegionViewModel
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.root.debounceClickListener
import ru.practicum.android.diploma.util.thisName


class RegionFragment : ChooseFragment() {

    override val fragment = REGION
    override val viewModel: RegionViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    private var region: Region? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.applyBtn.debounceClickListener(debouncer) {
            region?.let { viewModel.saveRegion(it) }
            findNavController().navigateUp()
        }
        
        binding.search.doOnTextChanged { text, _, _, _ ->
            viewModel.log(thisName, "$text")
            viewModel.onSearchQueryChanged(text.toString())
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun renderContent(list: List<Any?>) {
        super.renderContent(list)
        
        binding.toolbar.title = requireActivity().getString(R.string.choose_region)
        filterAdapter.regionList = list as List<Region>
        binding.inputLayout.visibility = View.VISIBLE
        filterAdapter.notifyDataSetChanged()
        viewModel.log(thisName, "renderContent: list.size = ${list.size})")
    }

    override fun initListeners() {
        super.initListeners()
        
        filterAdapter.onClickRegion = { region ->
            super.hideKeyboard()
            this.region = region
            binding.applyBtn.visibility = View.VISIBLE
        }
    }

    companion object { const val REGION = "RegionFragment" }
}