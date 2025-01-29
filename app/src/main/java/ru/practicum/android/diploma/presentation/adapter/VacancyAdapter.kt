package ru.practicum.android.diploma.presentation.adapter

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy
import java.text.NumberFormat
import java.util.Locale

class VacancyAdapter : RecyclerView.Adapter<VacancyAdapter.VacancyViewHolder>() {

    private var vacancyList = mutableListOf<Vacancy>()

    fun updateItems(items: List<Vacancy>) {
        val oldItems = this.vacancyList
        val newItems = items.toMutableList()
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldItems.size
            }

            override fun getNewListSize(): Int {
                return newItems.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition].id == newItems[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldItems[oldItemPosition] == newItems[newItemPosition]
            }
        })

        this.vacancyList = newItems.toMutableList()
        diffResult.dispatchUpdatesTo(this)
    }

    var onItemClick: ((Vacancy) -> Unit)? = null

    inner class VacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val vacancyImage: ImageView = itemView.findViewById(R.id.vacancyImage)
        private val vacancyNameAndArea: TextView = itemView.findViewById(R.id.vacancyNameAndArea)
        private val vacancyEmployerName: TextView = itemView.findViewById(R.id.employerName)
        private val vacancySalary: TextView = itemView.findViewById(R.id.salary)

        fun bind(vacancy: Vacancy) {
            itemView.setOnClickListener { onItemClick?.let { it1 -> it1(vacancy) } }

            with(vacancyImage) {
                Glide.with(itemView)
                    .load(vacancy.logoUrl90)
                    .placeholder(R.drawable.image_placeholder)
                    .centerCrop()
                    .transform(
                        RoundedCorners(
                            TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                ROUNDED_CORNERS_IMAGE_VACANCY,
                                context.resources.displayMetrics
                            ).toInt()
                        )
                    )
                    .into(vacancyImage)
            }

            vacancyNameAndArea.text =
                itemView.resources.getString(R.string.item_vacancy_name, vacancy.name, vacancy.area)
            vacancyEmployerName.text = vacancy.employerName
            vacancySalary.text = getVacancySalaryText(vacancy.salary)
        }

        private fun getVacancySalaryText(salary: Salary?): String {
            val vacancyText: String
            if (salary == null) {
                vacancyText = itemView.resources.getString(R.string.emptySalary)

            } else {
                val currencySymbol = getCurrencySymbol(salary.currency!!)
                val numberFormat = NumberFormat.getInstance(Locale.getDefault())

                if (salary.from == null) {
                    vacancyText = itemView.resources.getString(
                        R.string.item_vacancy_salary_to,
                        numberFormat.format(salary.to).replace(",", " "),
                        currencySymbol
                    )

                } else if (salary.to == null) {
                    vacancyText = itemView.resources.getString(
                        R.string.item_vacancy_salary_from,
                        numberFormat.format(salary.from).replace(",", " "),
                        currencySymbol
                    )

                } else {
                    vacancyText = itemView.resources.getString(
                        R.string.item_vacancy_salary_from_to,
                        numberFormat.format(salary.from).replace(",", " "),
                        numberFormat.format(salary.to).replace(",", " "),
                        currencySymbol
                    )
                }
            }
            return vacancyText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        return VacancyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.vacancy_item_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return vacancyList.size
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(vacancyList[position])
    }

    companion object {
        private const val ROUNDED_CORNERS_IMAGE_VACANCY = 12F
    }
}

fun getCurrencySymbol(currency: String): String {
    return when (currency) {
        "BYR" -> "Br"
        "USD" -> "$"
        "EUR" -> "€"
        "KZT" -> "₸"
        "UAH" -> "₴"
        "AZN" -> "₼"
        "UZS" -> "сум"
        "GEL" -> "₾"
        "KGT" -> "сом"
        else -> "₽"
    }
}
