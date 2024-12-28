package ru.practicum.android.diploma.ui.filter.workplace.region

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentChoiceRegionBinding
import ru.practicum.android.diploma.domain.models.Region

class ChoiceRegionFragment : Fragment() {

    private var _binding: FragmentChoiceRegionBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModel<RegionsViewModel>()
    private var textWatcher: TextWatcher? = null
    private val adapter = RegionsAdapter { region ->
        selectRegion(region)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChoiceRegionBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFoundedRegion.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFoundedRegion.adapter = adapter

        val area = requireArguments().getSerializable(COUNTRY_ARG) as? String

        viewModel.getRegions(area)
        viewModel.state.observe(viewLifecycleOwner, ::renderState)

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchRegions(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                with(binding.flFrameSearchRegion) {
                    if (s.isNullOrBlank()) {
                        endIconMode = TextInputLayout.END_ICON_CUSTOM
                        setEndIconDrawable(R.drawable.search_icon)
                        findViewById<View>(com.google.android.material.R.id.text_input_end_icon).isClickable = false
                    } else {
                        setEndIconDrawable(R.drawable.search_clear_icon)
                        setEndIconOnClickListener {
                            s.clear()
                            viewModel.resetSearch()
                        }
                    }
                }
            }
        }
        binding.etSearchRegion.addTextChangedListener(textWatcher)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun selectRegion(region: Region) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(REGION_BACKSTACK_KEY, region)
        findNavController().previousBackStackEntry?.savedStateHandle?.set(COUNTRY_ARG, region.parentCountry)
        findNavController().popBackStack()
    }

    private fun renderState(state: RegionsState) {
        when (state) {
            is RegionsState.Loading -> showLoading()
            is RegionsState.Error -> showError()
            is RegionsState.Content -> showContent(state.data)
            is RegionsState.NotFound -> showNotFound()
        }
    }

    private fun showLoading() {
        binding.rvFoundedRegion.isVisible = false
        binding.placeholderNotFound.isVisible = false
        binding.placeholderNotConnect.isVisible = false
        binding.progressBar.isVisible = true
    }

    private fun showError() {
        binding.rvFoundedRegion.isVisible = false
        binding.placeholderNotConnect.isVisible = true
        binding.placeholderNotFound.isVisible = false
        binding.progressBar.isVisible = false
    }

    private fun showContent(data: List<Region>) {
        binding.rvFoundedRegion.isVisible = true
        binding.placeholderNotFound.isVisible = false
        binding.placeholderNotConnect.isVisible = false
        binding.progressBar.isVisible = false

        adapter.clear()
        adapter.setData(data)
    }

    private fun showNotFound() {
        with(binding) {
            progressBar.isVisible = false
            rvFoundedRegion.isVisible = false
            placeholderNotFound.isVisible = true
            placeholderNotConnect.isVisible = false
        }
    }

    companion object {
        const val COUNTRY_ARG = "country"
        const val REGION_BACKSTACK_KEY = "region_key"
        fun createArgs(countryId: String): Bundle = bundleOf(COUNTRY_ARG to countryId)
    }

}
