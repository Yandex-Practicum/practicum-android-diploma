package ru.practicum.android.diploma.ui.filters

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltersBinding

class FiltersFragment : Fragment() {

    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply.visibility = View.GONE
        binding.remove.visibility = View.GONE

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

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val place = binding.workplaceValue.text
                val check = binding.checkBox.isChecked
                val salary = binding.edit
                if (place != "Место работы" || check || salary.text.isNotEmpty()) {
                    binding.apply.visibility = View.VISIBLE
                    binding.remove.visibility = View.VISIBLE
                } else {
                    binding.apply.visibility = View.GONE
                    binding.remove.visibility = View.GONE
                }

                binding.clearIcon.visibility
                if (s?.isNotEmpty() == true) {
                    binding.clearIcon.visibility = View.VISIBLE
                    binding.expectedSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Blue))
                } else {
                    binding.clearIcon.visibility = View.GONE
                    binding.expectedSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Text_Gray))
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val place = binding.workplaceValue.text
                val check = binding.checkBox.isChecked
                val salary = binding.edit
                if (place != "Место работы" || check || salary.text.isNotEmpty()) {
                    binding.apply.visibility = View.VISIBLE
                    binding.remove.visibility = View.VISIBLE
                } else {
                    binding.apply.visibility = View.GONE
                    binding.remove.visibility = View.GONE
                }

                if (s?.isNotEmpty() == true) {
                    binding.clearIcon.visibility = View.VISIBLE
                    binding.expectedSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Blue))
                } else {
                    binding.clearIcon.visibility = View.GONE
                    binding.expectedSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Text_Gray))
                }
            }

            override fun afterTextChanged(s: Editable?) {
                val place = binding.workplaceValue.text
                val check = binding.checkBox.isChecked
                val salary = binding.edit
                if (place != "Место работы" || check || salary.text.isNotEmpty()) {
                    binding.apply.visibility = View.VISIBLE
                    binding.remove.visibility = View.VISIBLE
                } else {
                    binding.apply.visibility = View.GONE
                    binding.remove.visibility = View.GONE
                }

                if (s?.isNotEmpty() == true) {
                    binding.clearIcon.visibility = View.VISIBLE
                    binding.expectedSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Blue))
                } else {
                    binding.clearIcon.visibility = View.GONE
                    binding.expectedSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Text_Gray))
                }
            }
        }
        binding.edit.addTextChangedListener(textWatcher)

        binding.clearIcon.setOnClickListener {
            binding.edit.setText("")
        }

        binding.edit.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                if (binding.edit.text.isNotEmpty()) {
                    binding.clearIcon.visibility = View.VISIBLE
                    binding.expectedSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Blue))
                } else {
                    binding.clearIcon.visibility = View.GONE
                    binding.expectedSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Text_Gray))
                }
            } else {
                binding.clearIcon.visibility = View.GONE
                if (binding.edit.text.isNotEmpty()) {
                    binding.expectedSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Black))
                } else {
                    binding.expectedSalary.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Text_Gray))
                }
            }
        }

        binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            val place = binding.workplaceValue.text
            val check = binding.checkBox.isChecked
            val salary = binding.edit
            if (place != "Место работы" || check || salary.text.isNotEmpty()) {
                binding.apply.visibility = View.VISIBLE
                binding.remove.visibility = View.VISIBLE
            } else {
                binding.apply.visibility = View.GONE
                binding.remove.visibility = View.GONE
            }
        }

        setFragmentResultListener("requestKeyPlace") { _, result ->
            val data = result.getString("keyPlace")
            binding.workplaceValue.text = data
            binding.workplaceValue.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Black))
            val place = binding.workplaceValue.text
            val check = binding.checkBox.isChecked
            val salary = binding.edit
            if (place != "Место работы" || check || salary.text.isNotEmpty()) {
                binding.apply.visibility = View.VISIBLE
                binding.remove.visibility = View.VISIBLE
            } else {
                binding.apply.visibility = View.GONE
                binding.remove.visibility = View.GONE
            }

            if (place != "Место работы") {
                binding.workplaceView.setImageResource(R.drawable.close_icon)
            } else {
                binding.workplaceView.setImageResource(R.drawable.arrow_forward)
            }
        }

        binding.apply.setOnClickListener {
//            val bundle = Bundle()
//            val place = binding.workplaceValue.text.split(", ")
//            val country = place[0]
//            val region = place[1]
//            val check = binding.checkBox.isChecked
//            val industry = binding.industryValue.text.toString()
//            val salary = binding.edit.text.toString()
//            val result = Filter(country, region, industry, salary, check)
//            bundle.putString("key", Gson().toJson(result))
//            setFragmentResult("requestKey", bundle)
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
