package ru.practicum.android.diploma.filters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.IndustrySelectFragmentBinding

class IndustrySelectFragment : Fragment() {
    private lateinit var binding: IndustrySelectFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = IndustrySelectFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
