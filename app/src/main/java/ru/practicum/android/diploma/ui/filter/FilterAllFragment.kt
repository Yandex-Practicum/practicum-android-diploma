package ru.practicum.android.diploma.ui.filter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentAllFilterBinding
import ru.practicum.android.diploma.domain.filter.datashared.SalaryBooleanShared

class FilterAllFragment : Fragment() {

    private var _binding: FragmentAllFilterBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FilterAllViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAllFilterBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n", "SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.countryState.observe(viewLifecycleOwner) { country ->
            if (country != null) {
                binding.filterTextWorkplace.text = country.countryName
                binding.filterIcWorkplace.setImageResource(R.drawable.ic_close_24px)
                binding.filterIcWorkplace.isClickable = true
                binding.filterVisibleWorkplace.visibility = View.VISIBLE

                viewModel.regionState.observe(viewLifecycleOwner) { region ->
                    if (region != null)
                        binding.filterTextWorkplace.text = country.countryName + ", " + region.regionName
                }
            } else {
                binding.filterTextWorkplace.text = "Место работы"
                binding.filterTextWorkplace.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
                binding.filterIcWorkplace.setImageResource(R.drawable.arrow_forward_24px)
                binding.filterIcWorkplace.isClickable = false
                binding.filterVisibleWorkplace.visibility = View.GONE
            }
        }

        viewModel.industriesState.observe(viewLifecycleOwner) { industries ->

        }

        viewModel.salarySum.observe(viewLifecycleOwner) { salarySum ->
            if (salarySum?.salary?.isNotEmpty() == true) {
                binding.expectedSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                binding.filterTextSalary.setText(salarySum.salary)
                binding.filterTextSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                binding.filterSalaryClear.visibility = View.VISIBLE
                binding.filterFunctionButton.visibility = View.VISIBLE
            } else {
                binding.expectedSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
                binding.filterTextSalary.setText("")
                binding.filterTextSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
                binding.filterSalaryClear.visibility = View.GONE
            }
        }

        viewModel.salaryBoolean.observe(viewLifecycleOwner) { salaryBoolean ->

        }

        binding.filterIcWorkplace.setOnClickListener {
            viewModel.setCountryInfo(null)
            viewModel.setRegionInfo(null)
        }

        binding.filterIcIndustries.setOnClickListener {
            viewModel.setIndustriesInfo(null)
        }

        binding.filterFunctionRemove.setOnClickListener {
            viewModel.setCountryInfo(null)
            viewModel.setRegionInfo(null)
            viewModel.setIndustriesInfo(null)
            viewModel.setSalarySumInfo(null)
            viewModel.setSalaryBooleanInfo(SalaryBooleanShared(false))
        }

        binding.filterWorkplace.setOnClickListener {
            findNavController().navigate(R.id.action_filterAllFragment_to_workplaceFragment)
        }

        binding.filterIndustries.setOnClickListener {
            findNavController().navigate(R.id.action_filterAllFragment_to_industriesFragment)
        }

        binding.filterToolbar.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
