package ru.practicum.android.diploma.filter.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.filter.ui.view_models.RegionViewModel
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.root.debounceClickListener
import ru.practicum.android.diploma.util.thisName


class RegionFragment : ChooseFragment() {

    override val fragment = REGION
    override val viewModel: RegionViewModel by viewModels { (activity as RootActivity).viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.applyBtn.debounceClickListener(debouncer) { findNavController().navigateUp() }
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
        filterAdapter.notifyItemRangeChanged(0, filterAdapter.itemCount)
        viewModel.log(thisName, "renderContent: list.size = ${list.size})")
    }

    override fun initListeners() {
        super.initListeners()
        filterAdapter.onClickRegion = { region ->
            viewModel.saveRegion(region)
            binding.applyBtn.visibility = View.VISIBLE
            // сделать в Item колечко кружочком
        }
    }

    companion object { const val REGION = "RegionFragment" }
}