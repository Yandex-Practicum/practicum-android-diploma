package ru.practicum.android.diploma.search.ui

import android.icu.text.SimpleDateFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.VacancyItems
import java.util.Locale

class VacancyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName: TextView = itemView.findViewById(R.id.job)
    val tvEmployer: TextView = itemView.findViewById(R.id.company)
    val ivIcon: ImageView = itemView.findViewById(R.id.vacancyIcon)
    val tvSalary: TextView = itemView.findViewById(R.id.salary)

   /* val id: String,
    val name: String, // Название вакансии
    val areaName: String, // Город
    val employer: String, // Компания
    val iconUrl: String? = null, // Иконка компании
    val salary: Salary? = null, // Зарплатка в наносекунду) */
   fun bind(
       item: VacancyItems,
       onItemClickListener: OnItemClickListener
   ) {
       tvName.text = item.name
       tvEmployer.text = item.employer
           /* tvSalary.text = item.salary

       Glide.with(itemView)
           .load(item.artworkUrl100)
           .placeholder(R.drawable.place_holder)
           .fitCenter()
           .transform(RoundedCorners(2))//(R.dimen.corner_radius_2))
           .into(ivImage)

       itemView.setOnClickListener {
           onItemClickListener?.onItemClick(item)
       }*/
   }

    interface OnItemClickListener {
        fun onItemClick(item: VacancyItems)
    }
}
