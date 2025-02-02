package ru.practicum.android.diploma.filter.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterPlaceOfWorkBinding
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterPlaceOfWorkViewModel
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterScreenViewModel

class FilterPlaceOfWorkFragment : Fragment() {
    private var _binding: FragmentFilterPlaceOfWorkBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FilterPlaceOfWorkViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFilterPlaceOfWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topBar.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSelectPlaceOfWork.isVisible = false
        Glide.with(this)
            .load(R.drawable.ic_arrow_forward_24px)
            .centerCrop()
            .into(binding.buttonImageCountry)
        Glide.with(this)
            .load(R.drawable.ic_arrow_forward_24px)
            .centerCrop()
            .into(binding.buttonImageRegion)

        binding.buttonImageCountry .setOnClickListener {
            findNavController().navigate(
                R.id.action_filterPlaceOfWorkFragment_to_filterCountriesFragment
            )
        }

        binding.buttonImageRegion .setOnClickListener {
            findNavController().navigate(
                R.id.action_filterPlaceOfWorkFragment_to_filterRegionFragment
            )
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
