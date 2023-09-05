package ru.practicum.android.diploma.filter.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Region
import ru.practicum.android.diploma.root.debounceClickListener
import ru.practicum.android.diploma.util.thisName


class RegionFragment : CountryFilterFragment() {

    override val holder = "Region"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editText.doOnTextChanged { text, _, _, _ ->
            viewModel.log(thisName, "$text")
            viewModel.onSearchQueryChanged(text.toString())
        }

    }

    override fun initAdapterListener() {
        filterAdapter.onClickRegion = { region ->
            viewModel.regionArgs = region
            binding.applyBtn.visibility = View.VISIBLE
            // сделать в Item колечко кружочком
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun refreshList(list: List<Any?>) {
        binding.searchContainer.visibility = View.VISIBLE
        filterAdapter.regionList = list as List<Region>
    }

}