package ru.practicum.android.diploma.ui.filter.place.region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentRegionFilterBinding
import ru.practicum.android.diploma.util.handleBackPress

class RegionFilterFragment : Fragment() {
    private var _binding: FragmentRegionFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegionFilterBinding.inflate(inflater, container, false)
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
        toolbar.setToolbarTitle(getString(R.string.region))
        toolbar.setupToolbarBackButton(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
