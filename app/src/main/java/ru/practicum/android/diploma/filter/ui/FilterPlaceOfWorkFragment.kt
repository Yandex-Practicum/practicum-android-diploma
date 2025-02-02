package ru.practicum.android.diploma.filter.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterPlaceOfWorkBinding
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterPlaceOfWorkViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.filter.domain.model.Country
import ru.practicum.android.diploma.filter.domain.model.Region

class FilterPlaceOfWorkFragment : Fragment() {
    private var _binding: FragmentFilterPlaceOfWorkBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<FilterPlaceOfWorkViewModel>()
    private var selectCountry: Country? = null
    private var selectRegion: Region? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFilterPlaceOfWorkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        selectRegion = viewModel.getRegion().value
        selectCountry = viewModel.getCountry().value
        Log.i(
            "MyTest",
            "FilterPlaceOfWorkFragment: onViewCreated(), \n  selectCountry = " + selectCountry + ", selectRegion = " + selectRegion
        )

        viewModel.getCountry().observe(viewLifecycleOwner) { _country ->
            if (_country != null) {
                binding.smallAndBigCountry.isVisible = false
                binding.onlyBigCountry.isVisible = true
                //binding.btnSelectPlaceOfWork.isVisible = true
                Glide.with(this)
                    .load(R.drawable.ic_arrow_forward_24px)
                    .centerCrop()
                    .into(binding.buttonImageCountry)
            } else {
                binding.smallAndBigCountry.isVisible = true
                binding.onlyBigCountry.isVisible = false
                // if(viewModel.getRegion().value == null)
                //    binding.btnSelectPlaceOfWork.isVisible = true
                Glide.with(this)
                    .load(R.drawable.ic_arrow_forward_24px)
                    .centerCrop()
                    .into(binding.buttonImageCountry)
            }
            selectCountry = _country
            binding.btnSelectPlaceOfWork.isVisible = (_country != null) && (selectRegion != null)
        }

        viewModel.getRegion().observe(viewLifecycleOwner) { _region ->
            if (_region != null) {
                binding.smallAndBigRegion.isVisible = false
                binding.onlyBigRegion.isVisible = true
                //  binding.btnSelectPlaceOfWork.isVisible = true
                Glide.with(this)
                    .load(R.drawable.ic_close_24px)
                    .centerCrop()
                    .into(binding.buttonImageRegion)
            } else {
                binding.smallAndBigRegion.isVisible = true
                binding.onlyBigRegion.isVisible = false
                //if(viewModel.getCountry().value == null)
                //    binding.btnSelectPlaceOfWork.isVisible = true
                Glide.with(this)
                    .load(R.drawable.ic_arrow_forward_24px)
                    .centerCrop()
                    .into(binding.buttonImageRegion)
            }
            selectRegion = _region
            binding.btnSelectPlaceOfWork.isVisible = (_region != null) && (selectCountry != null)
        }

        binding.topBar.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSelectPlaceOfWork.setOnClickListener { // в чем разница с "назад"?
            findNavController().navigateUp()
        }

        binding.buttonImageCountry.setOnClickListener {
            if (selectCountry == null)
                findNavController().navigate(
                    R.id.action_filterPlaceOfWorkFragment_to_filterCountriesFragment
                )
            else { // нажали на крестик, надо очистить страну в настройках фильтра
                //viewModel.delCountry()
            }
        }

        binding.buttonImageRegion.setOnClickListener {
            if (selectRegion == null)
                findNavController().navigate(
                    R.id.action_filterPlaceOfWorkFragment_to_filterRegionFragment
                )
            else { // нажали на крестик, надо очистить регион в настройках фильтра
                //viewModel.delRegion()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }

}
