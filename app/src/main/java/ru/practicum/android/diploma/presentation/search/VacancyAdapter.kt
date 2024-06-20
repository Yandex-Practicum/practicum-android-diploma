package ru.practicum.android.diploma.presentation.search

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemVacancyViewBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyAdapter :
    RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder>() {
    private var vacancyList = arrayListOf<Vacancy>()
    private var itemVacancyClickListener: ItemVacancyClickInterface? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        return VacancyViewHolder(
            ItemVacancyViewBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            itemVacancyClickListener
        )
    }

    override fun getItemCount() = vacancyList.size
    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancyList[position])
    }

    fun setVacancyList(vacancyList: ArrayList<Vacancy>) {
        this.vacancyList = vacancyList
        notifyDataSetChanged()
    }

    interface ItemVacancyClickInterface {
        fun onItemVacancyClick(vacancy: Vacancy)
    }

    fun setInItemVacancyClickListener(itemClickListener: ItemVacancyClickInterface) {
        this.itemVacancyClickListener = itemClickListener
    }

    class VacancyViewHolder(
        binding: ItemVacancyViewBinding,
        private val itemVacancyClickListener: ItemVacancyClickInterface?
    ) : RecyclerView.ViewHolder(binding.root) {

        private val vacancyNameAndCity: TextView = binding.textViewVacancyNameAndCity
        private val employerName: TextView = binding.textViewEmployerName
        private val vacancySalary: TextView = binding.textViewVacancySalary
        private val employerLogoUrlImage: ImageView = binding.imageViewEmployerLogoUrl
        private var itemVacancy: Vacancy? = null

        init {
            itemView.setOnClickListener {
                itemVacancy?.let { item ->
                    itemVacancyClickListener?.onItemVacancyClick(
                        item
                    )
                }
            }
        }

        fun bind(vacancy: Vacancy) {
            itemVacancy = vacancy
            vacancyNameAndCity.text = java.lang.String(
                vacancy.name
                    + itemView.context.getString(R.string.comma)
                    + whitespace
                    + vacancy.city
            )
            employerName.text = vacancy.employerName
            vacancySalary.text = setSalary(vacancy)

            Glide.with(itemView)
                .load(vacancy.employerLogoUrl)
                .placeholder(R.drawable.placeholder_android)
                .centerCrop()
                .transform(RoundedCorners(dpToPx(cornerRadius, itemView.context)))
                .into(employerLogoUrlImage)
        }

        private fun dpToPx(dp: Float, context: Context): Int {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.resources.displayMetrics
            ).toInt()
        }

        private fun setSalary(vacancy: Vacancy): String {
            var salary = ""
            if (vacancy.salaryFrom == null && vacancy.salaryTo == null) {
                salary = itemView.context.getString(R.string.salary_is_not_specified)
            } else {
                if (vacancy.salaryFrom != null) {
                    salary =
                        java.lang.String(
                            itemView.context.getString(R.string.from)
                                + whitespace
                                + vacancy.salaryFrom.toString()
                        ).toString()
                }
                if (vacancy.salaryTo != null) {
                    salary =
                        java.lang.String(
                            salary
                                + whitespace
                                + itemView.context.getString(R.string.to)
                                + whitespace
                                + vacancy.salaryTo
                        ).toString()
                }
            }
            return if (vacancy.salaryCurrency != null) {
                salary + whitespace + vacancy.salaryCurrency
            } else {
                salary
            }
        }
    }

    companion object {
        const val cornerRadius: Float = 12f
        const val whitespace = " "
    }
}
