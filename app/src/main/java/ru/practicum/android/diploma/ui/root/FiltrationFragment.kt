package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding

class FiltrationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentFilterBinding.inflate(layoutInflater)
        val returnButton = binding.returnButton

        returnButton.setOnClickListener {
            findNavController().navigate(R.id.action_filtrationFragment_to_mainFragment)
        }

        return binding.root
    }
}
