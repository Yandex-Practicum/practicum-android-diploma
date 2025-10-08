package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.models.SalaryFormatter
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.presentation.vmodels.VacancyDetailViewModel
import ru.practicum.android.diploma.ui.root.search.VacancyDetailState

class VacancyDetailFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!
    private val viewModel: VacancyDetailViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var vacancyId = arguments?.getString(ARGS_VACANCY_ID)
        vacancyId?:let {
            vacancyId = arguments?.getString(ARGS_VACANCY_ID_BY_DB)
            viewModel.loadDatabase(vacancyId)
        }
        viewModel.search(vacancyId)
        viewModel.checkFavorites(vacancyId)
        observeViewModel()
        binding.returnButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.favoriteButton.setOnClickListener {
            viewModel.addFavorites()
        }
        binding.shareButton.setOnClickListener {
            viewModel.shared()
        }
    }

    private fun observeViewModel() {
        viewModel.vacancyDetailState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is VacancyDetailState.Success -> getVacancyDetail(state.vacancyDetail)
            }
        }
        viewModel.vacancyFavoriteState.observe(viewLifecycleOwner) { state ->
            renderFavorites(state)
        }
    }

    private fun renderFavorites(value: Boolean) {
        if (value) {
            binding.favoriteButton.setImageResource(R.drawable.favorite_icon_red)
        } else {
            binding.favoriteButton.setImageResource(R.drawable.favorite_icon)
        }
    }

    private fun getVacancyDetail(vacancyDetail: VacancyDetail) {
        binding.vacancyTitle.text = vacancyDetail.name
        val salaryText = vacancyDetail.salary?.let { salary ->
            SalaryFormatter.getFormattedSalary(salary)
        } ?: getString(R.string.not_money)
        binding.salaryText.text = salaryText

        Glide.with(this)
            .load(vacancyDetail.employer?.logoUrl)
            .placeholder(R.drawable.ic_company_placeholder)
            .error(R.drawable.ic_company_placeholder)
            .into(binding.vacancyCover)

        binding.industry.text = vacancyDetail.industry?.name
        binding.area.text = vacancyDetail.area?.name
        binding.experience.text = vacancyDetail.experience?.name
        binding.schedule.text = vacancyDetail.schedule?.name
        binding.employment.text = vacancyDetail.employment?.name
        binding.description.text = vacancyDetail.description
        vacancyDetail.contact?.let {
            if (it.phone != null && it.email != "") {
                binding.contactGroup.isVisible = true
            }
            it.email?.let { email ->
                binding.contactEmail.text = email
                binding.contactEmail.setOnClickListener {
                    viewModel.sharedEmail(email)
                }
            }
            it.phone?.let { phone ->
                var str = ""
                phone.forEach { tel ->
                str += tel + "\n"
            }
                binding.contactPhone.text = str
                binding.contactPhone.setOnClickListener {
                    viewModel.sharedPhone(str)
                }
            }
        }
            }


    private companion object {
        private const val ARGS_VACANCY_ID = "vacancyId"
        private const val ARGS_VACANCY_ID_BY_DB = "vacancyIdByDatabase"
    }
}
