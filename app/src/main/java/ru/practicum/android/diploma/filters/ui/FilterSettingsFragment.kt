package ru.practicum.android.diploma.filters.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FilterSettingsFragmentBinding
import ru.practicum.android.diploma.util.hideKeyboard
import java.text.DecimalFormat


class FilterSettingsFragment : Fragment() {
    private var _binding: FilterSettingsFragmentBinding? = null
    private val binding get() = _binding!!

    private val formatter = DecimalFormat("#,###")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FilterSettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val salaryTextWatcher = object : TextWatcher {
            private var current = ""
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // коммент для детекта
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                clearButtonVisibility(p0)
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() != current) {
                    val cleanString = s.toString().replace("[^\\d]".toRegex(), "")
                    val parsed = cleanString.toLongOrNull()
                    val formatted = parsed?.let { formatter.format(it) } ?: ""

                    current = formatted
                    binding.editText.setText(formatted)
                    binding.editText.setSelection(formatted.length)
                }
            }

        }

        binding.editText.addTextChangedListener(salaryTextWatcher)

        binding.searchLineCleaner.setOnClickListener {
            clearSalary()
        }

        // пример заполнения поля
        binding.areaEditText.setOnClickListener {
            binding.areaEditText.setText("Россия, Москва")
            binding.areaEditText.isActivated = true
            binding.areaInputLayout.setEndIconDrawable(R.drawable.ic_close_24px)
        }

        binding.industryEditText.setOnClickListener {
            binding.apply {
                industryEditText.setText("IT")
                industryEditText.isActivated = true
                industryInputLayout.setEndIconDrawable(R.drawable.ic_close_24px)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clearButtonVisibility(s: CharSequence?) {
        val visibility = !s.isNullOrEmpty() && binding.editText.isFocused
        binding.searchLineCleaner.isVisible = visibility
    }

    private fun clearSalary() {
        binding.editText.setText(DEF_TEXT)
        view?.hideKeyboard()
        view?.clearFocus()
    }

    companion object {
        const val DEF_TEXT = ""
    }
}
