package ru.practicum.android.diploma.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentMainBinding
import ru.practicum.android.diploma.ui.main.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private val viewModel by viewModel<MainViewModel>()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchEditText.addTextChangedListener(simpleTextWatcher())

        binding.clearButton.setOnClickListener{
            clearSearchText()
        }

        binding.filterImageView.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_filtersFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun clearSearchText(){
        binding.searchEditText.setText("")
        binding.searchContainer.endIconMode = TextInputLayout.END_ICON_CUSTOM
        binding.searchContainer.endIconDrawable = requireContext().getDrawable(R.drawable.ic_search)
        binding.clearButton.isEnabled = false
    }

    private fun simpleTextWatcher() = object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        @SuppressLint("UseCompatLoadingForDrawables")
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            binding.searchContainer.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
            binding.searchContainer.endIconDrawable = requireContext().getDrawable(R.drawable.ic_clear)
            binding.clearButton.isEnabled = true
        }

        override fun afterTextChanged(s: Editable?) {}
    }
}
