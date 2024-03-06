package ru.practicum.android.diploma.filter.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding

class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        getData()
    }

    private fun initListeners() {
        binding.filterToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.placeOfJobNavigation.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_placeSelectorFragment)
        }
        binding.branchOfJobNavigation.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_branchFragment)
        }
        changeIcon(binding.branchOfJobText, binding.branchOfJobIcon)
        changeIcon(binding.placeOfJobText, binding.placeOfJobIcon)
        binding.salaryInputEditText.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrEmpty()) {
                binding.clearInput.visibility = View.GONE
            } else {
                binding.clearInput.visibility = View.VISIBLE
            }
        }
        binding.clearInput.setOnClickListener {
            binding.salaryInputEditText.setText("")
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(binding.salaryInputEditText.windowToken, 0)
        }
    }

    private fun getData() {
        setFragmentResultListener(FILTER_RECEIVER_KEY) { requestKey, bundle ->
            val country = bundle.getString(COUNTRY_KEY)
            val region = bundle.getString(REGION_KEY)
            val branch = bundle.getString(BRANCH_KEY)
        }
    }

    private fun changeIcon(editText: EditText, view: ImageView) {
        editText.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrEmpty()) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.GONE
            }
        }
    }

    companion object {
        const val BRANCH_KEY = "branchKey"
        const val REGION_KEY = "regionKey"
        const val COUNTRY_KEY = "countryKey"
        const val COUNTRY_ID_KEY = "countryIdKey"
        const val FILTER_RECEIVER_KEY = "filter_receiver_key"
    }
}
