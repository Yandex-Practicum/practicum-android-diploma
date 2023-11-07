package ru.practicum.android.diploma.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.data.dto.Phone
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.domain.DetailState
import ru.practicum.android.diploma.domain.models.detail.FullVacancy
import ru.practicum.android.diploma.presentation.SalaryPresenter
import ru.practicum.android.diploma.presentation.detail.adapter.PhoneAdapter
import ru.practicum.android.diploma.presentation.search.SearchFragment
import ru.practicum.android.diploma.util.debounce


class DetailFragment : Fragment() {
    private val viewModel by viewModel<DetailViewModel>()
    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    lateinit var onItemClickDebounce: (Phone) -> Unit

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
        viewModel.getVacancy(vacancyId)
        viewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

    }

    private fun render(state: DetailState) {
        when (state) {
            is DetailState.Loading -> showLoading()
            is DetailState.Content -> showContent(state.vacancy)
            is DetailState.Error -> showError(state.errorMessage)
        }
    }

    private fun showLoading() {
        binding.jobNameTv.text = "fdfdfdfdf"

    }

    private fun showContent(vacancy: FullVacancy) {
        binding.jobNameTv.text = vacancy.name
        binding.jobPaymentTv.text = SalaryPresenter().showSalary(vacancy.salary)
        binding.experienceTv.text = vacancy.experience
        binding.employerNameTv.text = vacancy.employerName
        binding.locationTv.text = vacancy.city
        binding.contactPersonName.text = vacancy.contacts?.name ?: ""
        binding.emailAddress.text = vacancy.contacts?.email ?: ""
        binding.phone.layoutManager =
            GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false)
        binding.phone.adapter = vacancy.contacts?.let {
            PhoneAdapter(it.phones) { phone ->
                onItemClickDebounce(phone)
            }
        }
        onItemClickDebounce = debounce(
            SearchFragment.CLICK_DEBOUNCE_DELAY_MILLIS,
            viewLifecycleOwner.lifecycleScope,
            false
        ) { phone ->

        }
        binding.skillsTv.text = vacancy.skills
        binding.requirementsTv.text = vacancy.requirements
    }


    private fun showError(errorMessage: String) {
        binding.jobNameTv.text = "aaaaaa"
    }


    companion object {
        private var vacancyId: String = ""

        fun addArgs(id: String) {
            vacancyId += id
        }
    }
}

/*binding.jobNameTv.text = vacancy.name
// binding.jobPaymentTv.text = SalaryPresenter().showSalary(vacancy.salary)
binding.experienceTv.text = vacancy.experience
binding.employerNameTv.text = vacancy.employer?.name ?: ""
binding.locationTv.text = vacancy.address
binding.contactPersonName.text = vacancy.contacts.name ?: ""
binding.emailAddress.text = vacancy.contacts.email ?: ""
binding.phone.layoutManager =
GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false)
binding.phone.adapter = PhoneAdapter(vacancy.contacts.phones) { phone ->
    onItemClickDebounce(phone)
}
onItemClickDebounce = debounce(
SearchFragment.CLICK_DEBOUNCE_DELAY_MILLIS,
viewLifecycleOwner.lifecycleScope,
false
) { phone ->

}
binding.skillsTv.text = vacancy.skills
binding.requirementsTv.text = vacancy.requirements*/