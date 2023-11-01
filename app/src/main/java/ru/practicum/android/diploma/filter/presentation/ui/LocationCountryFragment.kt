package ru.practicum.android.diploma.filter.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.databinding.FragmentLocationCountryBinding
import ru.practicum.android.diploma.filter.presentation.viewmodel.LocationCountryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LocationCountryFragment: Fragment() {
    private lateinit var binding: FragmentLocationCountryBinding

    private val viewModel: LocationCountryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btTopBarBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}