package ru.practicum.android.diploma.filter.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import ru.practicum.android.diploma.databinding.FragmentFilterLocationBinding
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterLocationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R

class FilterLocationFragment: Fragment() {
    private lateinit var binding: FragmentFilterLocationBinding

    private val viewModel: FilterLocationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.miLocationCountry.editText?.setText("Россия")
        setMenuEditTextStyle(binding.miLocationCountry, true)
        setMenuEditTextStyle(binding.miLocationRegion, false)

        binding.miLocationCountry.setOnClickListener {
            showLocation()
        }

        binding.miLocationCountry.setEndIconOnClickListener {
            binding.miLocationCountry.editText?.text = null
            setMenuEditTextStyle(binding.miLocationCountry, false)
        }

        binding.miLocationRegion.setEndIconOnClickListener {
            binding.miLocationRegion.editText?.text = null
            setMenuEditTextStyle(binding.miLocationRegion, false)
        }

        binding.btTopBarBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setMenuEditTextStyle(textInputLayout: TextInputLayout, filled: Boolean) {
        val coloInt = if (filled) {
            R.color.filter_menu_label_selector
        } else {
            R.color.filter_menu_hint_selector
        }

        val colorStateList = ResourcesCompat.getColorStateList(resources, coloInt, requireContext().theme)
        textInputLayout.setBoxStrokeColorStateList(colorStateList!!)
        textInputLayout.defaultHintTextColor = colorStateList
        textInputLayout.hintTextColor = colorStateList
        if (filled) {
            textInputLayout.setEndIconDrawable(R.drawable.ic_filter_clear)
        } else {
            textInputLayout.setEndIconDrawable(R.drawable.ic_filter_arrow_forward)
        }
    }

    private fun showLocation() {
        findNavController().navigate(R.id.action_filterLocationFragment_to_locationCountryFragment)
    }
}