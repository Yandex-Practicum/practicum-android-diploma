package ru.practicum.android.diploma.ui.filter

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFiltersBinding
import ru.practicum.android.diploma.ui.search.gone
import ru.practicum.android.diploma.ui.search.visible

class FilterFragment : Fragment() {

    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initButtonListeners()
        initTextListeners()

    }

    private fun initButtonListeners() {
        binding.arrowBackButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.placeOfWork.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_filterPlaceOfWorkFragment)
        }

        binding.industry.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_filterIndustryFragment)
        }

        binding.textView2.setOnClickListener {
            //TODO
        }

        binding.textView3.setOnClickListener {
            //TODO
        }

        binding.clearButton.setOnClickListener {
            binding.expectedSalary.setText("")
            hideKeyboard()
        }
    }

    private fun initTextListeners() {
        binding.expectedSalary.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButtonVisibility()
            }

            override fun afterTextChanged(p0: Editable?) { }
        }
        )
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(binding.expectedSalary.windowToken, 0)
    }

    private fun clearButtonVisibility() = if (binding.expectedSalary.text.isNullOrEmpty()) {
        binding.clearButton.gone()
    } else {
        binding.clearButton.visible()
    }
}
