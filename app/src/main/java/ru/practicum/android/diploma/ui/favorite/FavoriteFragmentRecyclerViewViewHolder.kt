package ru.practicum.android.diploma.ui.favorite

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy

class FavoriteFragmentRecyclerViewViewHolder(
    itemView: View,
    private val vacancyClicked: (Vacancy) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val vacancy_company_logo: ImageView = itemView.findViewById(R.id.iv_logo)
    private val vacancy_title: TextView = itemView.findViewById(R.id.tv_vacancy_name)
    private val vacancy_company: TextView = itemView.findViewById(R.id.tv_vacancy_type)
    private val vacancy_salary: TextView = itemView.findViewById(R.id.tv_vacancy_salary)

    fun bind(model: Vacancy) {

        vacancy_title.text = model.name
        vacancy_company.text = model.employer?.name ?: ""

        //TODO("добавить форматтер з/п")
        vacancy_salary.text =
            "от ${model.salary?.from ?: ""} до ${model.salary?.to ?: ""} ${model.salary?.currency ?: ""}END"
                .replace("от  до  END", "Зарплата не указана")
                .replace("от  ", "")
                .replace("до  ", "")
                .replace("END", "")

        Glide
            .with(itemView)
            .load(model.employer?.logoUrls ?: "")
            .placeholder(R.drawable.spiral)
            .transform(CenterCrop())
            .into(vacancy_company_logo)

        itemView.setOnClickListener { vacancyClicked(model) }
    }

}
