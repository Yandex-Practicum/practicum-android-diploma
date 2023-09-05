package ru.practicum.android.diploma.details.ui

import android.view.View
import android.widget.Toast
import androidx.core.text.HtmlCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentDetailsBinding
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.util.setImage

sealed interface DetailsScreenState {
    fun render(binding: FragmentDetailsBinding)
    object Empty : DetailsScreenState{
        override fun render(binding: FragmentDetailsBinding) = Unit
    }
    data class Content(val vacancy: VacancyFullInfo) : DetailsScreenState{
        override fun render(binding: FragmentDetailsBinding) {
            with(binding) {
                hideContactsIfEmpty(binding)
                showKeySkills(binding)
                val tvSchedule = vacancy.employment + ". " + vacancy.schedule
                val formattedDescription = HtmlCompat.fromHtml(vacancy.description, HtmlCompat.FROM_HTML_MODE_LEGACY)

                if (vacancy.logo.isNotEmpty()) imageView.imageTintList = null

                tvContactsPhone.text =
                    if (vacancy.contactPhones.isEmpty()) { binding.root.context.getString(R.string.no_info) }
                    else { vacancy.contactPhones.joinToString("\n") }

                tvContactsName.text = vacancy.contactName
                tvContactsEmail.text = vacancy.contactEmail
                tvExperience.text = vacancy.experience
                tvScheduleEmployment.text = tvSchedule
                tvDescription.text = formattedDescription
                tvNameOfVacancy.text = vacancy.title
                tvSalary.text = vacancy.salary
                tvNameOfCompany.text = vacancy.company
                tvArea.text = vacancy.area
                imageView.setImage(vacancy.logo, R.drawable.ic_placeholder_company, binding.root.context.resources.getDimensionPixelSize(R.dimen.size_12dp))
            }
        }

        private fun showKeySkills(binding: FragmentDetailsBinding) {
            with(binding) {
                if (vacancy.keySkills.isEmpty()) {
                    tvKeySkillsTitle.visibility = View.GONE
                    tvKeySkills.visibility = View.GONE
                } else {
                    tvKeySkills.text = vacancy.keySkills
                }
            }
        }

        private fun hideContactsIfEmpty(binding: FragmentDetailsBinding) {
            with(binding) {
                if (vacancy.contactName.isEmpty() &&
                    vacancy.contactEmail.isEmpty() &&
                    vacancy.contactPhones.isEmpty()
                ) {
                    tvContactsName.visibility = View.GONE
                    tvContactsEmail.visibility = View.GONE
                    tvContactsPhone.visibility = View.GONE
                    tvContactsTitle.visibility = View.GONE
                    tvEmail.visibility = View.GONE
                    tvPhone.visibility = View.GONE
                    tvContactsPerson.visibility = View.GONE
                }
            }
        }
    }

    data class Offline(val message: String) : DetailsScreenState {
        override fun render(binding: FragmentDetailsBinding) {
            Toast.makeText(binding.root.context, message, Toast.LENGTH_SHORT).show()
        }
    }

    data class Error(val message: String) : DetailsScreenState {
        override fun render(binding: FragmentDetailsBinding) {
            Toast.makeText(binding.root.context, message, Toast.LENGTH_SHORT).show()
        }
    }

    class PlayHeartAnimation(private val isInFavs: Boolean, val scope: CoroutineScope) :
        DetailsScreenState {
        override fun render(binding: FragmentDetailsBinding) {
            scope.launch {
                if (isInFavs) {
                    binding.lottieHeart.speed = STRAIGHT_ANIMATION_SPEED
                    binding.lottieHeart.playAnimation()
                } else {
                    binding.lottieHeart.speed = REVERS_ANIMATION_SPEED
                    binding.lottieHeart.playAnimation()
                }
            }
        }
    }
}
const val STRAIGHT_ANIMATION_SPEED = 1f
const val REVERS_ANIMATION_SPEED = -1f