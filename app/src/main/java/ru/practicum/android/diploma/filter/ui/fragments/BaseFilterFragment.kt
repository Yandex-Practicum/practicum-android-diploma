package ru.practicum.android.diploma.filter.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBaseBinding
import ru.practicum.android.diploma.util.viewBinding


class BaseFilterFragment : Fragment(R.layout.fragment_filter_base) {
    private val binding by viewBinding<FragmentFilterBaseBinding>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        binding.filterToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.workPlaceContainer.setOnClickListener {
            findNavController().navigate(
                resId = R.id.action_filterBaseFragment_to_workPlaceFilterFragment
            )
        }
    }
}