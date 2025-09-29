package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentMainBinding.inflate(layoutInflater)
        val trailingButton = binding.trailingButton

        trailingButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_filtrationFragment)
        }

        return binding.root
    }
}
