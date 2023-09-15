package ru.practicum.android.diploma.details.ui

import android.view.View
import androidx.core.text.HtmlCompat
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentDetailsBinding
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.util.setImage
import java.lang.StringBuilder

class DetailsScreenPainter(private val binding: FragmentDetailsBinding) {

    private val context = binding.root.context
    fun showDataContent(vacancy: VacancyFullInfo) {
        with(binding) {
            scrollView.visibility = View.VISIBLE
            placeHolder.visibility = View.GONE
            iwAnim.visibility = View.GONE
            hideContactsIfEmpty(vacancy)
            showKeySkills(vacancy)
            val tvSchedule = StringBuilder().append(vacancy.employment).append(". ").append(vacancy.schedule).toString()
            val formattedDescription =
                HtmlCompat.fromHtml(vacancy.description, HtmlCompat.FROM_HTML_MODE_LEGACY)

            if (vacancy.logo.isNotEmpty()) imageView.imageTintList = null

            tvContactsPhone.text = vacancy.contactPhones.joinToString("\n")
            tvContactsComment.text = vacancy.contactComment
            tvContactsName.text = vacancy.contactName
            tvContactsEmail.text = vacancy.contactEmail
            tvExperience.text = vacancy.experience
            tvScheduleEmployment.text = tvSchedule
            tvDescription.text = formattedDescription
            tvNameOfVacancy.text = vacancy.title
            tvSalary.text = vacancy.salary
            tvNameOfCompany.text = vacancy.company
            tvArea.text = vacancy.area
            imageView.setImage(
                vacancy.logo,
                R.drawable.ic_placeholder_company,
                binding.root.context.resources.getDimensionPixelSize(R.dimen.size_12dp)
            )
            if (vacancy.isInFavorite){
                showAddAnimation()
            }
        }
    }

    private fun showKeySkills(vacancy: VacancyFullInfo) {
        with(binding) {
            if (vacancy.keySkills.isEmpty()) {
                tvKeySkillsTitle.visibility = View.GONE
                tvKeySkills.visibility = View.GONE
            } else {
                tvKeySkills.text = vacancy.keySkills
            }
        }
    }

    private fun hideContactsIfEmpty(vacancy: VacancyFullInfo) {
        with(binding) {
            if (vacancy.contactName.isEmpty()) {
                tvContactsName.visibility = View.GONE
                tvContactsPerson.visibility = View.GONE
            }
            if (vacancy.contactEmail.isEmpty()) {
                tvContactsEmail.visibility = View.GONE
                tvEmail.visibility = View.GONE
            }
            if (vacancy.contactPhones.isEmpty()) {
                tvContactsPhone.visibility = View.GONE
                tvPhone.visibility = View.GONE
            }
            if (vacancy.contactComment.isEmpty()) {
                tvContactsComment.visibility = View.GONE
                tvComment.visibility = View.GONE
            }
            if (vacancy.contactName.isEmpty() &&
                vacancy.contactEmail.isEmpty() &&
                vacancy.contactPhones.isEmpty()
            ) {
                tvContactsTitle.visibility = View.GONE
            }
        }
    }

    fun showOffline() {
        binding.scrollView.visibility = View.GONE
        binding.iwAnim.visibility = View.GONE
        binding.placeHolderText.text = context.getString(R.string.no_internet_message)
        binding.placeHolder.visibility = View.VISIBLE
    }

    fun showError() {
        binding.scrollView.visibility = View.GONE
        binding.placeHolderText.text = context.getString(R.string.server_error)
        binding.placeHolder.visibility = View.VISIBLE
    }

    fun showLoading() {
        binding.scrollView.visibility = View.GONE
        binding.iwAnim.visibility = View.VISIBLE
    }

    fun showAddAnimation() {
            binding.lottieHeart.speed = STRAIGHT_ANIMATION_SPEED
            binding.lottieHeart.playAnimation()

    }

    fun showDeleteAnimation() {
            binding.lottieHeart.speed = REVERS_ANIMATION_SPEED
            binding.lottieHeart.playAnimation()

    }

    companion object {
        const val STRAIGHT_ANIMATION_SPEED = 4f
        const val REVERS_ANIMATION_SPEED = -4f
    }
}
