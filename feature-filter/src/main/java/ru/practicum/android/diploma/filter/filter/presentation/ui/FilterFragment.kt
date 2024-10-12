package ru.practicum.android.diploma.filter.filter.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.commonutils.Utils.closeKeyBoard
import ru.practicum.android.diploma.filter.R
import ru.practicum.android.diploma.filter.databinding.FragmentFilterBinding

internal class FilterFragment : Fragment() {
    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.workPlace.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_placeFragment)
        }
        binding.workProfession.setOnClickListener {
            findNavController().navigate(R.id.action_filterFragment_to_professionFragment)
        }
        binding.buttonLeftFilter.setOnClickListener {
            findNavController().navigateUp()
        }
        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.clearIconFilter.visibility = clearButtonVisibility(s)
            }
            override fun afterTextChanged(s: Editable?) {
            }
        }
        binding.editTextFilter.addTextChangedListener(simpleTextWatcher)
        binding.clearIconFilter.setOnClickListener {
            binding.editTextFilter.setText("")
            requireContext().closeKeyBoard(binding.editTextFilter)
        }

    }
    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
