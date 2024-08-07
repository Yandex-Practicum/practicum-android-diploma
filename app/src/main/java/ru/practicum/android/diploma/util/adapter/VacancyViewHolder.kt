package ru.practicum.android.diploma.util.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.SearchItemViewBinding
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyViewHolder(
    private val binding: SearchItemViewBinding,
    private val onClick: (Vacancy) -> Unit,
    private val onLongClick: (Vacancy) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Vacancy) {
        with(binding) {
            if (item.area?.name?.isNotBlank() == true) {
                val title = buildString {
                    append(item.name)
                    append(", ")
                    append(item.area.name)
                }
                positionTitle.text = title
            } else {
                positionTitle.text = item.name
            }

            // Устанавливает название компании
            companyTitle.text = item.employer?.name

            // Устанавливает зарплату, конвертируя ее в строку
            salaryTitle.text = converterSalaryToString(item.salary?.from, item.salary?.to, item.salary?.currency)

            // Устанавливает обработчики кликов
            root.setOnClickListener { onClick(item) }
            root.setOnLongClickListener {
                onLongClick(item)
                true
            }

            // Загрузка и отображение логотипа компании
            Glide.with(itemView)
                .load(item.employer?.logoUrls?.logo90)
                .placeholder(R.drawable.ic_placeholder_30px)
                .centerCrop()
                .transform(
                    RoundedCorners(
                        itemView.resources.getDimensionPixelSize(R.dimen.dimen_12dp)
                    )
                )
                .into(itemLogo)
        }
    }
}
