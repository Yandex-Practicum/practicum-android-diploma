package ru.practicum.android.diploma.filter.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentWorkPlaceFilterBinding
import ru.practicum.android.diploma.util.viewBinding


class WorkPlaceFilterFragment : Fragment(R.layout.fragment_work_place_filter) {
    private val binding by viewBinding<FragmentWorkPlaceFilterBinding>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initListeners()
    }


    private fun initListeners() {
        binding.filterToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}