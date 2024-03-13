package ru.practicum.android.diploma.ui.filters

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltersBinding
import ru.practicum.android.diploma.domain.filter.datashared.SalaryBooleanShared
import ru.practicum.android.diploma.domain.filter.datashared.SalaryTextShared

class FiltersFragment : Fragment() {

    private val viewModel by viewModel<FilterViewModel>()

    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!

    val handler = Handler(Looper.getMainLooper())

    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            if (start > 0) {
                binding.clearIcon.visibility = View.VISIBLE
            } else {
                binding.clearIcon.visibility = View.GONE
            }
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.clearIcon.visibility = View.VISIBLE
        }

        override fun afterTextChanged(s: Editable?) {
            handler.removeCallbacksAndMessages(null)

            handler.postDelayed({
                s?.toString()?.let {
                    viewModel.setSalaryTextInfo(
                        SalaryTextShared(
                            salary = it
                        )
                    )
                }
            }, DELAY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initFilterInfo()

        val regionName = viewModel.regionState.value?.regionName
        Log.d("RegionInfoState", "regionName = $regionName")

        viewModel.countryState.observe(viewLifecycleOwner) { country ->
            if (country != null) {
                binding.workplaceValue.text = country.countryName
                binding.workplaceValue.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_white))
                binding.workplaceView.setImageResource(R.drawable.close_icon)
                binding.workplaceView.isClickable = true
                binding.workplaceHint.visibility = View.VISIBLE
                binding.filterFunctionButton.visibility = View.VISIBLE

                viewModel.regionState.observe(viewLifecycleOwner) { region ->
                    if (region != null) {
                        binding.workplaceValue.text = country.countryName + ", " + region.regionName
                    } else {
                        binding.workplaceValue.text = country.countryName
                    }
                }
            } else {
                binding.workplaceView.setImageResource(R.drawable.arrow_forward)
                binding.workplaceView.isClickable = false
                binding.workplaceHint.visibility = View.GONE
            }
        }

        viewModel.industriesState.observe(viewLifecycleOwner) { industries ->
            if (industries != null) {
                binding.industryValue.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_white))
                binding.industryValue.text = industries.industriesName
                binding.industryView.setImageResource(R.drawable.close_icon)
                binding.industryView.isClickable = true
                binding.industryHint.visibility = View.VISIBLE
                binding.filterFunctionButton.visibility = View.VISIBLE
            } else {
                binding.industryView.setImageResource(R.drawable.arrow_forward)
                binding.industryView.isClickable = false
                binding.industryHint.visibility = View.GONE
            }
        }

        viewModel.salaryTextState.observe(viewLifecycleOwner) { salaryText ->
            if (salaryText?.salary?.isNotEmpty() == true) {
                binding.expectedSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_white))
                binding.edit.setText(salaryText.salary)
                binding.edit.setTextColor(ContextCompat.getColor(requireContext(), R.color.black_white))
                binding.clearIcon.visibility = View.VISIBLE
                binding.filterFunctionButton.visibility = View.VISIBLE
            } else {
                binding.expectedSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Text_Gray))
                binding.edit.setText("")
                binding.edit.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Text_Gray))
                binding.clearIcon.visibility = View.GONE
            }
        }

        viewModel.salaryBooleanState.observe(viewLifecycleOwner) { salaryBoolean ->
            binding.checkBox.isChecked = salaryBoolean?.isChecked ?: false
        }

        binding.workplaceView.setOnClickListener {
            binding.workplaceValue.text = "Место работы"
            binding.workplaceValue.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Text_Gray))
            binding.workplaceView.setImageResource(R.drawable.arrow_forward)
            binding.workplaceValue.isClickable = false
            binding.workplaceHint.visibility = View.GONE
            viewModel.setWorkplaceInfo(null, null)
        }

        binding.industryView.setOnClickListener {
            binding.industryValue.text = "Отрасль"
            binding.industryValue.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Text_Gray))
            binding.industryView.setImageResource(R.drawable.arrow_forward)
            binding.industryView.isClickable = false
            binding.industryHint.visibility = View.GONE
            viewModel.setIndustriesInfo(null)
        }

        binding.edit.addTextChangedListener(textWatcher)

        binding.clearIcon.setOnClickListener {
            binding.edit.setText("1")
        }

        binding.vacancyToolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.workplace.setOnClickListener {
            findNavController().navigate(
                R.id.action_filtersFragment_to_workplaceFragment,
            )
        }

        binding.industry.setOnClickListener {
            findNavController().navigate(
                R.id.action_filtersFragment_to_industryFragment
            )
        }

        binding.apply.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.remove.setOnClickListener {
            viewModel.setWorkplaceInfo(null, null)
            viewModel.setIndustriesInfo(null)
            viewModel.setSalaryTextInfo(null)
            viewModel.setSalaryBooleanInfo(null)
            binding.filterFunctionButton.visibility = View.GONE
        }

        binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            // Обновляем состояние флажка в viewModel
            viewModel.setSalaryBooleanInfo(
                SalaryBooleanShared(
                    isChecked = isChecked
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val DELAY = 2000L
    }
}
