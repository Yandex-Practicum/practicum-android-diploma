package ru.practicum.android.diploma.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.databinding.FragmentModelBinding

open class ModelFragment : Fragment() {
    private var _binding: FragmentModelBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarInclude.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbarInclude.favourite.isVisible = false
        binding.toolbarInclude.share.isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}