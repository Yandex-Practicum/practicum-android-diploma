package ru.practicum.android.diploma.filters.areas.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.AreaSelectFragmentBinding
import ru.practicum.android.diploma.filters.areas.presentation.AreaSelectViewModel

class AreaSelectFragment : Fragment() {
    private var _binding: AreaSelectFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AreaSelectViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AreaSelectFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeInit()
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.countrySelect.setOnClickListener {
            findNavController().navigate(R.id.action_workingRegionFragment_to_countrySelectFragment)
        }

        binding.regionSelect.setOnClickListener {
            findNavController().navigate(R.id.action_workingRegionFragment_to_citySelectFragment)
        }

        binding.areaInputLayout.setEndIconOnClickListener {
            viewModel.clearAreaFilter()
        }
        binding.regionInputLayout.setEndIconOnClickListener {
            viewModel.clearRegionFilter()
        }
    }

    private fun observeInit() {
        /*    viewModel.getCountryAndRegionStateMap.observe(viewLifecycleOwner) { fields ->
                if (fields.country.isNotEmpty()) {
                    binding.countrySelect.apply {
                        setText(fields.country)
                        isActivated = true
                    }
                    binding.countryInputLayout.setEndIconDrawable(R.drawable.ic_close_24px)
                } else {
                    binding.countrySelect.apply {
                        setText("")
                        isActivated = false
                    }
                    binding.countryInputLayout.setEndIconDrawable(R.drawable.ic_arrow_forward_24px)
                }

                if (fields.region.isNotEmpty()) {
                    binding.regionSelect.apply {
                        setText(fields.region)
                        isActivated = true
                    }
                    binding.regionInputLayout.setEndIconDrawable(R.drawable.ic_close_24px)
                } else {
                    binding.regionSelect.apply {
                        setText("")
                        isActivated = false
                    }
                    binding.regionSelect.setEndIconDrawable(R.drawable.ic_arrow_forward_24px)
                }
            }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
