package ru.practicum.android.diploma.filter.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentRegionDepartmentBinding
import ru.practicum.android.diploma.filter.ui.fragments.adapters.CountryFilterAdapter
import ru.practicum.android.diploma.filter.ui.models.FilterScreenState
import ru.practicum.android.diploma.filter.ui.view_models.CountryViewModel
import ru.practicum.android.diploma.filter.ui.view_models.RegionViewModel
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.util.thisName
import ru.practicum.android.diploma.util.viewBinding
import javax.inject.Inject


class RegionFragment : CountryFilterFragment() {

    override val binding by viewBinding<FragmentRegionDepartmentBinding>()
    @Inject lateinit var regionAdapter: CountryFilterAdapter
    private val viewModel: RegionViewModel by viewModels { (activity as RootActivity).viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { return binding.root }


    override fun initAdapterListeners() {
        regionAdapter.onItemClick = { region ->
            findNavController().navigate(
                RegionFragmentDirections.action_regionFragment_to_workPlaceFilterFragment(
                    null,
                    region
                )
            )
        }
    }


    private fun initAdapter() {
        binding.recycler.adapter = regionAdapter
    }
}