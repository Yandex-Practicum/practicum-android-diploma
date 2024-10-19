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
import ru.practicum.android.diploma.filters.areas.presentation.BaseAreaSelectViewModel

class BaseAreaSelectFragment : Fragment() {
    private var _binding: AreaSelectFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BaseAreaSelectViewModel by viewModel()

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
        viewModel.updateFields()
        observeInit()
        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.applyButton.setOnClickListener {
            viewModel.saveArea()
            findNavController().popBackStack()
        }
        countryAndRegionSelectClickListenerInit()
    }

    private fun countryAndRegionSelectClickListenerInit() {
        binding.countrySelect.setOnClickListener {
            findNavController().navigate(
                R.id.action_workingRegionFragment_to_countrySelectFragment
            )
        }

        binding.regionSelect.setOnClickListener {
            findNavController().navigate(
                R.id.action_workingRegionFragment_to_citySelectFragment
            )
        }

        binding.countryInputLayout.setEndIconOnClickListener {
            if (!binding.countrySelect.text.isNullOrBlank()) {
                viewModel.clearAreaFilter()
            } else {
                findNavController().navigate(
                    R.id.action_workingRegionFragment_to_countrySelectFragment
                )
            }
        }
        binding.regionInputLayout.setEndIconOnClickListener {
            if (!binding.regionSelect.text.isNullOrBlank()) {
                viewModel.clearRegionFilter()
            } else {
                findNavController().navigate(
                    R.id.action_workingRegionFragment_to_citySelectFragment
                )
            }
        }
    }

    private fun observeInit() {
        viewModel.getCountryAndRegionStateMap.observe(viewLifecycleOwner) { fields ->
            if (fields.first.isNotEmpty()) {
                binding.countrySelect.apply {
                    setText(fields.first)
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

            if (fields.second.isNotEmpty()) {
                binding.regionSelect.apply {
                    setText(fields.second)
                    isActivated = true
                }
                binding.regionInputLayout.setEndIconDrawable(R.drawable.ic_close_24px)
            } else {
                binding.regionSelect.apply {
                    setText("")
                    isActivated = false
                }
                binding.regionInputLayout.setEndIconDrawable(R.drawable.ic_arrow_forward_24px)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
