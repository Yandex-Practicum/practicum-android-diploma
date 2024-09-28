package ru.practicum.android.diploma.filters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.CitySelectFragmentBinding

class CitySelectFragment : Fragment() {
    private lateinit var binding: CitySelectFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CitySelectFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
