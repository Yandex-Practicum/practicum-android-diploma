package ru.practicum.android.diploma.ui.filter.place.country

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentCountryFilterBinding
import ru.practicum.android.diploma.util.handleBackPress

class CountryFilterFragment : Fragment() {
    private var _binding: FragmentCountryFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUiToolbar()
        // системная кн назад
        handleBackPress()
    }

    private fun initUiToolbar() {
        // настройка кастомного топбара
        val toolbar = binding.toolbar
        toolbar.setupToolbarForFilterScreen()
        toolbar.setToolbarTitle(getString(R.string.country))
        toolbar.setupToolbarBackButton(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
