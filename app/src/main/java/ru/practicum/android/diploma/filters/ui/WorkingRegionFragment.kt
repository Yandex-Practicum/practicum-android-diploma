package ru.practicum.android.diploma.filters.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.WorkingRegionFragmentBinding

class WorkingRegionFragment : Fragment() {
    private lateinit var binding: WorkingRegionFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WorkingRegionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}
