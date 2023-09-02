package ru.practicum.android.diploma.filter.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkPlaceFilterBinding
import ru.practicum.android.diploma.util.viewBinding


class WorkPlaceFilterFragment : Fragment(R.layout.fragment_work_place_filter) {
    private val binding by viewBinding<FragmentWorkPlaceFilterBinding>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        with(binding) {
            filterToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            countryContainer.setOnClickListener {
                findNavController().navigate(
                    WorkPlaceFilterFragmentDirections.actionWorkPlaceFilterFragmentToCountryFilterFragment()
                )
            }
            regionContainer.setOnClickListener {
                findNavController().navigate(
                    WorkPlaceFilterFragmentDirections.actionWorkPlaceFilterFragmentToRegionFragment()
                )
            }
        }
    }
}