package ru.practicum.android.diploma.ui.filter

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentAllFilterBinding
import ru.practicum.android.diploma.domain.debugLog
import ru.practicum.android.diploma.domain.filter.datashared.SalaryBooleanShared
import ru.practicum.android.diploma.domain.filter.datashared.SalaryTextShared

class FilterAllFragment : Fragment() {

    private var _binding: FragmentAllFilterBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<FilterAllViewModel>()

    val handler = Handler(Looper.getMainLooper())

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            if (start > 0) {
                binding.filterSalaryClear.visibility = View.VISIBLE
            } else {
                binding.filterSalaryClear.visibility = View.GONE
            }
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.filterSalaryClear.visibility = View.VISIBLE

            if (s?.isNotEmpty() == true) {
                binding.filterExpectedSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                binding.filterTextSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                binding.filterSalaryClear.visibility = View.VISIBLE
                binding.filterFunctionButton.visibility = View.VISIBLE
            } else {
                binding.filterExpectedSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
                binding.filterTextSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
                binding.filterSalaryClear.visibility = View.GONE
            }
        }

        override fun afterTextChanged(s: Editable?) {
            handler.removeCallbacksAndMessages(null)

            handler.postDelayed({
                s?.toString()?.let {
                    viewModel.setSalarySumInfo(
                        SalaryTextShared(
                            salary = it
                        )
                    )
                }
            }, DELAY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAllFilterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeCountryState()
        observeIndustriesState()
        observeSalarySum()
        observeSalaryBoolean()
        bindTextWatcher()
        bindOnClickListeners()
        bindNavigationListeners()
    }

    @SuppressLint("SetTextI18n", "SuspiciousIndentation")
    private fun observeCountryState() = with(binding) {
        viewModel.countryState.observe(viewLifecycleOwner) { country ->
            if (country != null) {
                filterTextWorkplace.text = country.countryName
                filterIcWorkplace.setImageResource(R.drawable.ic_close_24px)
                filterIcWorkplace.isClickable = true
                filterVisibleWorkplace.visibility = View.VISIBLE

                viewModel.regionState.observe(viewLifecycleOwner) { region ->
                    if (region != null) {
                        filterTextWorkplace.text = country.countryName + ", " + region.regionName
                    }
                }
            } else {
                filterTextWorkplace.text = "Место работы"
                filterTextWorkplace.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
                filterIcWorkplace.setImageResource(R.drawable.arrow_forward_24px)
                filterIcWorkplace.isClickable = false
                filterVisibleWorkplace.visibility = View.GONE
            }
        }
    }

    private fun observeIndustriesState() = with(binding) {
        viewModel.industriesState.observe(viewLifecycleOwner) { industries ->
            if (industries != null) {
                filterTextIndustries.text = industries.industriesName
                filterIcIndustries.setImageResource(R.drawable.ic_close_24px)
                filterIcIndustries.isClickable = true
                filterVisibleIndustries.visibility = View.VISIBLE

            } else {
                filterTextIndustries.text = "Отрасль"
                filterTextIndustries.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
                filterIcIndustries.setImageResource(R.drawable.arrow_forward_24px)
                filterIcIndustries.isClickable = false
                filterVisibleIndustries.visibility = View.GONE
            }
        }
    }

    private fun observeSalarySum() = with(binding) {
        viewModel.salarySum.observe(viewLifecycleOwner) { salarySum ->
            if (salarySum?.salary?.isNotEmpty() == true) {
                filterExpectedSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                filterTextSalary.setText(salarySum.salary)
                filterTextSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                filterSalaryClear.visibility = View.VISIBLE
                filterSalaryClear.isClickable = true
                filterFunctionButton.visibility = View.VISIBLE
            } else {
                filterExpectedSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
                filterTextSalary.setText("")
                filterTextSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.grey))
                filterSalaryClear.visibility = View.GONE
            }
        }
    }

    private fun observeSalaryBoolean() = with(binding) {
        viewModel.salaryBoolean.observe(viewLifecycleOwner) { salaryBoolean ->
            debugLog(TAG) { "filterFunctionCheckbox, isChecked = ${salaryBoolean?.isChecked}" }
            filterFunctionCheckbox.isChecked = salaryBoolean?.isChecked ?: false
        }
    }

    private fun bindOnClickListeners() = with(binding) {
        filterSalaryClear.setOnClickListener {
            viewModel.setSalarySumInfo(null)
        }

        filterFunctionCheckbox.setOnCheckedChangeListener { _, isChecked ->
            // Обновляем состояние флажка в viewModel
            viewModel.setSalaryBooleanInfo(
                SalaryBooleanShared(
                    isChecked = isChecked
                )
            )
        }

        filterIcWorkplace.setOnClickListener {
            viewModel.setCountryInfo(null)
            viewModel.setRegionInfo(null)
        }

        filterIcIndustries.setOnClickListener {
            viewModel.setIndustriesInfo(null)
        }

        filterFunctionRemove.setOnClickListener {
            viewModel.setCountryInfo(null)
            viewModel.setRegionInfo(null)
            viewModel.setIndustriesInfo(null)
            viewModel.setSalarySumInfo(null)
            viewModel.setSalaryBooleanInfo(SalaryBooleanShared(false))
        }
    }

    private fun bindTextWatcher() = with(binding) {
        filterTextSalary.addTextChangedListener(textWatcher)
    }

    private fun bindNavigationListeners() = with(binding) {
        filterFunctionApply.setOnClickListener {
            findNavController().navigateUp()
        }

        filterWorkplace.setOnClickListener {
            findNavController().navigate(R.id.action_filterAllFragment_to_workplaceFragment)
        }

        filterIndustries.setOnClickListener {
            findNavController().navigate(R.id.action_filterAllFragment_to_industriesFragment)
        }

        filterToolbar.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "FilterAllFragment"
        const val DELAY = 2000L
    }
}
