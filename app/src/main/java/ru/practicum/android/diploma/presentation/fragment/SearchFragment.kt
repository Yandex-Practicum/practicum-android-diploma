package ru.practicum.android.diploma.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.practicum.android.diploma.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = requireNotNull(_binding) {"Binding wasn't initiliazed"}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}
