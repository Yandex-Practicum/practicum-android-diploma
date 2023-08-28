package ru.practicum.android.diploma.details.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentDetailsBinding
import ru.practicum.android.diploma.root.RootActivity
import ru.practicum.android.diploma.util.viewBinding


class DetailsFragment : Fragment(R.layout.fragment_details) {
private val binding by viewBinding<FragmentDetailsBinding>()
private val viewModel : DetailsViewModel by viewModels { (activity as RootActivity).viewModelFactory }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.toString()
    }
}