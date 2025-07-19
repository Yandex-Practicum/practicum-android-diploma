package ru.practicum.android.diploma.ui.searchfilters

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.color.MaterialColors
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.SearchFiltersFragmentBinding

class SearchFiltersFragment : Fragment() {

    private var _binding: SearchFiltersFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = SearchFiltersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextWorkplace.setOnClickListener {
            findNavController().navigate(R.id.action_searchFiltersFragment_to_workplaceFiltersFragment)
        }

        binding.editText.doOnTextChanged { text, start, before, count ->
            val query = text?.toString()

            if (query?.isNotEmpty() == true && binding.editText.hasFocus()) {
                binding.topHint.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                binding.icon.isVisible = true
                binding.icon.setOnClickListener {
                    binding.editText.setText("")
                }

            } else if (query?.isNotEmpty() == true) {
                binding.topHint.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            } else {
                binding.icon.isVisible = false
                binding.topHint.setTextColor(
                    MaterialColors.getColor(
                        requireContext(),
                        com.google.android.material.R.attr.colorOnContainer,
                        Color.BLACK
                    )
                )
            }
        }
        binding.arrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
