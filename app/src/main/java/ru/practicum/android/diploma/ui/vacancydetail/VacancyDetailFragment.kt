package ru.practicum.android.diploma.ui.vacancydetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyDetailBinding
import ru.practicum.android.diploma.domain.detail.VacancyDetail
import ru.practicum.android.diploma.domain.detail.VacancyPhoneAndComment
import ru.practicum.android.diploma.presentation.detail.DetailAdapter
import ru.practicum.android.diploma.ui.similarvacancies.SimilarVacanciesFragment
import ru.practicum.android.diploma.ui.vacancydetail.viewmodel.DetailViewModel
import ru.practicum.android.diploma.util.checkIfNotNull
import ru.practicum.android.diploma.util.extensions.visibleOrGone

class VacancyDetailFragment : Fragment() {

    private val vacancyDetailViewModel: DetailViewModel by viewModel()
    private var _binding: FragmentVacancyDetailBinding? = null
    private var adapter: DetailAdapter? = null
    private val binding get() = _binding!!
    private var vacancyLink = ""
    private var email = ""
    private var vacancyId = ""
    private var convertedVacancyId = ""
    private var phonesAndComments = mutableListOf<VacancyPhoneAndComment>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        convertedVacancyId = requireArguments().getString(VACANCYID)!!

        vacancyDetailViewModel.searchDetailInformation(convertedVacancyId)

        adapter = DetailAdapter(requireContext())
        binding.phonesAndCommentsRecyclerView.adapter = adapter
        binding.phonesAndCommentsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

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

        binding.tvcontactEmail.setOnClickListener {
            openEmail(email)
        }

        vacancyDetailViewModel.observeState().observe(viewLifecycleOwner) {
            renderState(it)
        }

        binding.ivbuttonLike.setOnClickListener {
            vacancyDetailViewModel.onFavoriteClicked()
        }

        binding.bbuttonSimilar.setOnClickListener {
            findNavController().navigate(
                R.id.action_vacanciesFragment_to_similarVacanciesFragment,
                SimilarVacanciesFragment.createArgs(vacancyId)
            )
        }
    }

    private fun renderState(state: DetailState) {
        when (state) {
            is DetailState.Loading -> {}
            is DetailState.Error -> {}
            is DetailState.Content -> {
                val newImageRes =
                    if (state.vacancyDetail.isFavorite) R.drawable.like_icon_off_in_on else R.drawable.like_icon_off
                binding.ivbuttonLike.setImageResource(newImageRes)
            }
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
        val newImageRes =
            if (vacancyDetail.isFavorite) R.drawable.like_icon_off_in_on else R.drawable.like_icon_off
        binding.ivbuttonLike.setImageResource(newImageRes)
        binding.tvvacancyName.text = vacancyDetail.name
        checkIfNotNullSalary(vacancyDetail.salary, binding.tvsalary)
        showIcon(vacancyDetail)
        binding.tvcompanyName.text = vacancyDetail.employerName
        binding.tvcompanyArea.text = vacancyDetail.address ?: vacancyDetail.area
        binding.tvexperiencelabel.visibleOrGone(
            checkIfNotNull(
                vacancyDetail.experience,
                binding.tvexperience
            )
        )
        showEmploymentAndSchedule(vacancyDetail)
        binding.tvdescription.setText(
            Html.fromHtml(
                vacancyDetail.description,
                Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM
            )
        )
        checkKeySkills(vacancyDetail.keySkills, binding.tvkeySkills)
        showContacts(vacancyDetail)
    }

    private fun checkKeySkills(vacancyDetailItemList: List<String?>, view: TextView) {
        if (vacancyDetailItemList.isNotEmpty()) {
            view.visibility = View.VISIBLE
            binding.tvkeySkillsLabel.visibility = View.VISIBLE
            view.text = vacancyDetailItemList
                .joinToString(separator = "\n") {
                    HtmlCompat.fromHtml("&#8226  $it", HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM)
                }
        } else {
            view.visibility = View.GONE
            binding.tvkeySkillsLabel.visibility = View.GONE
        }
    }

    private fun checkIfNotNullSalary(itemString: String?, view: TextView): Boolean {
        return if (itemString != null) {
            view.visibility = View.VISIBLE
            view.text = itemString.replace(",", " ")
            true
        } else {
            view.visibility = View.GONE
            false
        }
    }

    private fun checkPhonesAndComments(
        phonesList: List<String?>,
        commentsList: List<String?>,
    ): Boolean {
        return if (phonesList.isNotEmpty() || commentsList.isNotEmpty()) {
            binding.phonesAndCommentsRecyclerView.visibility = View.VISIBLE
            val maxValue = phonesList.size.coerceAtLeast(commentsList.size)
            phonesAndComments = (1..maxValue).map {
                VacancyPhoneAndComment(
                    contactPhone = phonesList[it - 1],
                    contactComment = commentsList[it - 1]
                )
            }.toMutableList()

            adapter?.phoneAndCommentList?.clear()
            adapter?.phoneAndCommentList?.addAll(phonesAndComments)
            adapter?.notifyDataSetChanged()
            true
        } else {
            binding.phonesAndCommentsRecyclerView.visibility = View.GONE
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
        binding.tvcomma.visibleOrGone(checkEmployment && checkSchedule)
    }

    private fun showContacts(vacancyDetail: VacancyDetail) {
        val checkContactName = checkIfNotNull(
            vacancyDetail.contactName,
            binding.tvcontactName
        )

        val checkContactEmail = checkIfNotNull(
            vacancyDetail.contactEmail,
            binding.tvcontactEmail
        )
        if (checkContactEmail) email = vacancyDetail.contactEmail!!

        val checkPhonesAndComments = checkPhonesAndComments(
            vacancyDetail.contactPhones,
            vacancyDetail.contactComments
        )

        binding.tvcontactsLabel.visibleOrGone(
            checkContactName
                || checkContactEmail
                || checkPhonesAndComments
        )

        binding.tvcontactNameLabel.visibleOrGone(checkContactName)
        binding.tvcontactEmailLabel.visibleOrGone(checkContactEmail)
    }

    private fun shareVacancy(link: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, link)
        val chooserIntent = Intent.createChooser(shareIntent, "Поделиться")
        chooserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        requireContext().startActivity(chooserIntent)
    }

    private fun openEmail(emailData: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:$emailData")
        emailIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        requireContext().startActivity(emailIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private var VACANCYID = "vacancy_id"
    }
}
