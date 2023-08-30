package ru.practicum.android.diploma.filter.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentRegionDepartmentBinding
import ru.practicum.android.diploma.util.viewBinding


class RegionFragment : Fragment(R.layout.fragment_region_department) {
    private val binding by viewBinding<FragmentRegionDepartmentBinding>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}