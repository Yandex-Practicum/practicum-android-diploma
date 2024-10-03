package ru.practicum.android.diploma.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import ru.practicum.android.diploma.databinding.VacancySearchFragmentBinding

class VacancySearchFragment : Fragment() {
    private var _binding: VacancySearchFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = VacancySearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.defaultSearchPlaceholder.isVisible = false
        binding.notConnectedPlaceholder.isVisible = false
        binding.notFoundPlaceholder.isVisible = false
        binding.serverErrorPlaceholder.isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
