package ru.practicum.android.diploma.filters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.CountrySelectFragmentBinding

class CountrySelectFragment : Fragment() {
    private lateinit var binding: CountrySelectFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CountrySelectFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
