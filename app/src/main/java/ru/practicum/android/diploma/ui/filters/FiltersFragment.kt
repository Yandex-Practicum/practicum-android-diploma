package ru.practicum.android.diploma.ui.filters

import android.content.Context
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
import com.google.gson.Gson
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltersBinding
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.ui.workplace.WorkplaceFragment

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
        binding.workplaceHint.visibility = View.GONE
        binding.industryHint.visibility = View.GONE

        val sharedPrefs = context?.getSharedPreferences(WorkplaceFragment.COUNTRY_PREFERENCES, Context.MODE_PRIVATE)

        var countryText = "Страна"
        var regionText = ""
        if (sharedPrefs?.getString(WorkplaceFragment.COUNTRY_TEXT, "")?.isNotEmpty() == true) {
            countryText = sharedPrefs.getString(WorkplaceFragment.COUNTRY_TEXT, "")!!
            binding.workplaceValue.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Black))
            binding.workplaceValue.text = "$countryText"
            binding.workplaceView.setImageResource(R.drawable.close_icon)
            binding.workplaceView.isClickable = true
            binding.workplaceHint.visibility = View.VISIBLE
        } else {
            binding.workplaceView.setImageResource(R.drawable.arrow_forward)
            binding.workplaceView.isClickable = false
            binding.workplaceHint.visibility = View.GONE
        }
        if (sharedPrefs?.getString(WorkplaceFragment.REGION_TEXT, "")?.isNotEmpty() == true) {
            regionText = sharedPrefs.getString(WorkplaceFragment.REGION_TEXT, "")!!
            binding.workplaceValue.text = "$countryText, $regionText"
        }

        binding.workplaceView.setOnClickListener {
            binding.workplaceValue.text = "Место работы"
            binding.workplaceValue.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Text_Gray))
            binding.workplaceView.setImageResource(R.drawable.arrow_forward)
            binding.workplaceValue.isClickable = false
            binding.workplaceHint.visibility = View.GONE
            sharedPrefs?.edit()?.putString(WorkplaceFragment.COUNTRY_TEXT, "")?.apply()
            sharedPrefs?.edit()?.putString(WorkplaceFragment.COUNTRY_ID, "")?.apply()
            sharedPrefs?.edit()?.putString(WorkplaceFragment.REGION_TEXT, "")?.apply()
            sharedPrefs?.edit()?.putString(WorkplaceFragment.REGION_ID, "")?.apply()
        }

        binding.industryView.setOnClickListener {
            binding.industryValue.text = "Отрасль"
            binding.industryValue.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Text_Gray))
            binding.industryView.setImageResource(R.drawable.arrow_forward)
            binding.industryView.isClickable = false
            binding.industryHint.visibility = View.GONE
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

        if (sharedPrefs?.getString(WorkplaceFragment.COUNTRY_TEXT, "")
                ?.isNotEmpty() == true || binding.edit.text.isNotEmpty() || binding.checkBox.isChecked
        ) {
            binding.apply.visibility = View.VISIBLE
            binding.remove.visibility = View.VISIBLE
        }

        binding.remove.setOnClickListener {
            binding.workplaceValue.text = "Место работы"
            binding.workplaceValue.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Text_Gray))
            binding.workplaceView.setImageResource(R.drawable.arrow_forward)
            binding.workplaceValue.isClickable = false
            sharedPrefs?.edit()?.putString(WorkplaceFragment.COUNTRY_TEXT, "")?.apply()
            sharedPrefs?.edit()?.putString(WorkplaceFragment.COUNTRY_ID, "")?.apply()
            sharedPrefs?.edit()?.putString(WorkplaceFragment.REGION_TEXT, "")?.apply()
            sharedPrefs?.edit()?.putString(WorkplaceFragment.REGION_ID, "")?.apply()
            binding.checkBox.isChecked = false
            binding.workplaceHint.visibility = View.GONE
            binding.industryHint.visibility = View.GONE
            binding.edit.setText("")
        }

        val sharedPreferences = context?.getSharedPreferences(FILTER_PREFERENCES, Context.MODE_PRIVATE)

        binding.apply.setOnClickListener {
            var country = null
            var region = null
            /*if(binding.workplaceValue.text.isNotEmpty()){
                val place = binding.workplaceValue.text.split(", ")
                if(place.size == 2){
                    country = place[0]
                    region = place[1]
                }
            }*/

            var industry = null

            /*if(binding.industryValue.text.isNotEmpty()){
                industry = binding.industryValue.text.toString()
            }*/

            val check = binding.checkBox.isChecked
            var salary = binding.edit.text.toString()
            if(salary.isEmpty()){
                salary = "5000"
            }
            val result = Filter(salary = salary, onlyWithSalary = check, country = country, region = region, industry = industry)
            sharedPreferences?.edit()?.putString(FILTER, Gson().toJson(result))?.apply()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val FILTER_PREFERENCES = "filter_preferences"
        const val FILTER = "filter"
    }
}
