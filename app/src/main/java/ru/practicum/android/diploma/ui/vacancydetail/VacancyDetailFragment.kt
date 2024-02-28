package ru.practicum.android.diploma.ui.vacancydetail

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyDetailBinding
import ru.practicum.android.diploma.domain.models.detail.VacancyDetail
import ru.practicum.android.diploma.ui.similarvacancies.SimilarVacanciesFragment
import ru.practicum.android.diploma.ui.vacancydetail.viewmodel.DetailViewModel

class VacancyDetailFragment : Fragment() {

    companion object {
        private const val VACANCYID = "vacancy_id"
        fun createArgs(vacancyId: String): Bundle =
            bundleOf(
                VACANCYID to vacancyId
            )
    }

    private val vacancyDetailViewModel by viewModel<DetailViewModel>()
    private var _binding: FragmentVacancyDetailBinding? = null
    private val binding get() = _binding!!
    private var vacancyLink = ""
    private var vacancyId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val convertedVacancyId = requireArguments().getString(VACANCYID)!!

        vacancyDetailViewModel.searchDetailInformation(convertedVacancyId)

        vacancyDetailViewModel.observeState().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.navigationToolbar.setOnClickListener {
            findNavController().popBackStack()
        }

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        })

        binding.ivbuttonShare.setOnClickListener {
            shareVacancy(vacancyLink)
        }

        binding.ivbuttonLike.setOnClickListener {
            //сделать лайк/анлайк
        }

        binding.bbuttonSimilar.setOnClickListener {
            findNavController().navigate(
                R.id.action_vacanciesFragment_to_similarVacanciesFragment,
                SimilarVacanciesFragment.createArgs(vacancyId)
            )
        }

    }

    private fun render(state: DetailState) {
        when (state) {
            is DetailState.Loading -> showLoading()
            is DetailState.Error -> showError(state.errorMessage)
            is DetailState.Content -> showContent(state.vacancyDetail)
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.clvacancy.visibility = View.GONE
        binding.ivplaceholderImage.visibility = View.GONE
        binding.tvplaceholderMessage.visibility = View.GONE
    }

    private fun showError(errorMessage: Int) {
        binding.progressBar.visibility = View.GONE
        binding.clvacancy.visibility = View.GONE
        binding.ivplaceholderImage.visibility = View.VISIBLE
        binding.tvplaceholderMessage.visibility = View.VISIBLE
        binding.tvplaceholderMessage.setText(errorMessage)
    }

    private fun showContent(vacancyDetail: VacancyDetail) {
        binding.progressBar.visibility = View.GONE
        binding.clvacancy.visibility = View.VISIBLE
        binding.ivplaceholderImage.visibility = View.GONE
        binding.tvplaceholderMessage.visibility = View.GONE
        setView(vacancyDetail)
        vacancyLink = vacancyDetail.vacancyLink
        vacancyId = vacancyDetail.id
    }

    private fun setView(vacancyDetail: VacancyDetail) {
        binding.tvvacancyName.text = vacancyDetail.name
        checkIfNotNull(vacancyDetail.salary, binding.tvsalary)
        showIcon(vacancyDetail)
        binding.tvcompanyName.text = vacancyDetail.employerName
        binding.tvcompanyArea.text = vacancyDetail.area
        if (checkIfNotNull(vacancyDetail.experience, binding.tvexperience)) {
            binding.tvexperiencelabel.visibility = View.VISIBLE
        }
        showEmploymentAndSchedule(vacancyDetail)
        binding.tvdescription.setText(Html.fromHtml(vacancyDetail.description, Html.FROM_HTML_MODE_COMPACT))
        //пока только один скилл
        if (checkIfNotNull(vacancyDetail.keySkills, binding.tvkeySkills)) {
            binding.tvkeySkillsLabel.visibility = View.VISIBLE
        }
        showContacts(vacancyDetail)
    }

    private fun checkIfNotNull(vacancyDetailItem: String?, view: TextView): Boolean {
        return if (vacancyDetailItem != null) {
            view.visibility = View.VISIBLE
            view.text = vacancyDetailItem
            true
        } else {
            view.visibility = View.GONE
            false
        }
    }

    private fun showIcon(vacancyDetail: VacancyDetail) {
        Glide.with(this)
            .load(vacancyDetail.employerUrl)
            .placeholder(R.drawable.placeholder_company_icon)
            .centerCrop()
            .into(binding.ivcompanyIcon)
    }

    private fun showEmploymentAndSchedule(vacancyDetail: VacancyDetail) {
        val checkEmployment = checkIfNotNull(vacancyDetail.employment, binding.tvemployment)
        val checkSchedule = checkIfNotNull(vacancyDetail.schedule, binding.tvschedule)
        if (checkEmployment && checkSchedule) binding.tvcomma.visibility = View.VISIBLE
    }

    private fun showContacts(vacancyDetail: VacancyDetail) {
        val checkContactName = checkIfNotNull(vacancyDetail.contactName, binding.tvcontactName)
        val checkContactEmail = checkIfNotNull(vacancyDetail.contactEmail, binding.tvcontactEmail)
        //пока только один телефон и коммент
        val checkContactPhone = checkIfNotNull(vacancyDetail.contactPhone, binding.tvcontactPhone)
        val checkContactComment = checkIfNotNull(vacancyDetail.contactComment, binding.tvcontactComment)
        if (checkContactName || checkContactEmail || checkContactPhone || checkContactComment) {
            binding.tvcontactsLabel.visibility = View.VISIBLE
        }
        if (checkContactName) binding.tvcontactNameLabel.visibility = View.VISIBLE
        if (checkContactEmail) binding.tvcontactEmailLabel.visibility = View.VISIBLE
        if (checkContactPhone) binding.tvcontactPhoneLabel.visibility = View.VISIBLE
        if (checkContactComment) binding.tvcontactCommentLabel.visibility = View.VISIBLE
    }

    private fun shareVacancy(link: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, link)
        val chooserIntent = Intent.createChooser(shareIntent, null)
        chooserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        requireContext().startActivity(chooserIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
