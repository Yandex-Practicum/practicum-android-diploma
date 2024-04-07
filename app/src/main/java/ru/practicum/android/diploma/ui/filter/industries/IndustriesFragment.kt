package ru.practicum.android.diploma.ui.filter.industries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentIndustriesBinding
import ru.practicum.android.diploma.ui.filter.workplace.country.CountryState
import ru.practicum.android.diploma.ui.filter.workplace.country.CountryViewModel

class IndustriesFragment : Fragment() {

    private val viewModel by viewModel<IndustriesViewModel>()

    private var _binding: FragmentIndustriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentIndustriesBinding.inflate(layoutInflater)
        return binding.root

        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is IndustriesState.Content -> {
                    //TODO
                }

                is IndustriesState.Empty -> showEmpty(getString(state.message))
                is IndustriesState.Error -> showError(getString(state.errorMessage))
                is IndustriesState.Loading -> showLoading()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.industriesToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showContent() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.errorContainerLinearLayout.visibility = View.GONE
        binding.countryProgressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.recyclerView.visibility = View.GONE
        binding.errorContainerLinearLayout.visibility = View.GONE
        binding.countryProgressBar.visibility = View.VISIBLE
    }

    private fun showError(errorMessage: String) {
        binding.recyclerView.visibility = View.GONE
        binding.errorContainerLinearLayout.visibility = View.VISIBLE
        binding.errorImageView.setImageResource(R.drawable.state_image_error_get_list)
        binding.errorTextView.text = errorMessage
        binding.countryProgressBar.visibility = View.GONE
    }

    private fun showEmpty(message: String) {
        binding.recyclerView.visibility = View.GONE
        binding.errorContainerLinearLayout.visibility = View.VISIBLE
        binding.errorImageView.setImageResource(R.drawable.state_image_nothing_found)
        binding.errorTextView.text = message
        binding.countryProgressBar.visibility = View.GONE
    }
}
