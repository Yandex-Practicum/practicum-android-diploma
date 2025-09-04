package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterBinding

class FiltrationFragment : Fragment() {
    private lateinit var binding: FragmentFilterBinding
    private lateinit var returnButton: ImageButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFilterBinding.inflate(layoutInflater)
        returnButton = binding.returnButton
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        returnButton.setOnClickListener {
            findNavController().navigate(R.id.action_filtrationFragment_to_mainFragment)
        }
    }
}
