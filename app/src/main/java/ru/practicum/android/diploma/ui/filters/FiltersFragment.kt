package ru.practicum.android.diploma.ui.filters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltersBinding
import ru.practicum.android.diploma.domain.models.Filter

class FiltersFragment : Fragment() {

    private lateinit var binding: FragmentFiltersBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                R.id.action_filtersFragment_to_industryFragment,
            )
        }

        setFragmentResultListener("requestKeyPlace") { _, result ->
            val data = result.getString("keyPlace")
            binding.workplaceValue.text = data
            binding.workplaceValue.setTextColor(ContextCompat.getColor(requireContext(), R.color.YP_Black))
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
}
