package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.R
import java.text.NumberFormat
import java.util.Locale

object SalaryFormater {
    fun salaryFormater(context: Context, from: Int?, to: Int?, currency: String?): String{
        val fromOnly = context.getString(R.string.salary_half_mask)
        val toOnly = context.getString(R.string.salary_half_mask)
        val fromAndTo = context.getString(R.string.salary_full_mask)

        val numberFormat: NumberFormat = NumberFormat.getInstance(Locale("ru", "RU"))


        return when{
            fromOnly != null -> {
                fromOnly.format(numberFormat.format(from), currency)}

            toOnly != null -> {
                toOnly.format(numberFormat.format(to), currency)}

            fromOnly != null && toOnly != null -> {
                fromAndTo.format(numberFormat.format(from), numberFormat.format(to), currency)}

            else -> "Зарплата не указана"
        }
    }
}
