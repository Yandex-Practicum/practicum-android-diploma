package ru.practicum.android.diploma.ui.favorite

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.util.SalaryFormatter

class FavoriteVacanciesRecyclerViewViewHolder(
    itemView: View,
    private val vacancyClicked: (VacancyDetails) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val vacancyCompanyLogo: ImageView = itemView.findViewById(R.id.iv_logo)
    private val vacancyTitle: TextView = itemView.findViewById(R.id.tv_vacancy_name)
    private val vacancyCompany: TextView = itemView.findViewById(R.id.tv_vacancy_type)
    private val vacancySalary: TextView = itemView.findViewById(R.id.tv_vacancy_salary)

    fun bind(model: VacancyDetails) {
        vacancyTitle.text = model.name
        vacancyCompany.text = model.employer?.name ?: ""

        val salaryFrom = SalaryFormatter.format(model.salary?.from?.toString() ?: "")
        val salaryTo = SalaryFormatter.format(model.salary?.to?.toString() ?: "")

        if (salaryFrom != salaryTo) {
            vacancySalary.text =
                "от ${salaryFrom} до ${salaryTo} ${model.salary?.currency ?: ""}END"
                    .replace("от  до  END", itemView.resources.getString(R.string.salary_not_specified))
                    .replace("от  ", "")
                    .replace("до  ", "")
                    .replace("END", "")
                    .replace("от", itemView.resources.getString(R.string.from))
                    .replace("до", itemView.resources.getString(R.string.to))
        } else {
            vacancySalary.text =
                "${salaryTo} ${model.salary?.currency ?: ""}END"
                    .replace(" END", itemView.resources.getString(R.string.salary_not_specified))
                    .replace("END", "")
        }

        Glide
            .with(itemView)
            .load(model.employer?.logoUrls?.original ?: "")
            .placeholder(R.drawable.spiral)
            // .transform(CenterCrop())
            .transform(FitCenter())
            .into(vacancyCompanyLogo)

        itemView.setOnClickListener { vacancyClicked(model) }
    }
}
