package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.R

fun getSalaryStr(salaryFrom: String, salaryTo: String, currencyStr: String, context: Context): String {
    var resStr = ""
    if (salaryFrom.isNotBlank()) {
        resStr = resStr + "от " + salaryFrom + " "
    }

    if (salaryTo.isNotBlank()) {
        resStr = resStr + "до " + salaryTo
    }

    if (resStr.isNotBlank()) {
        resStr = "$resStr $currencyStr"
    } else {
        resStr = context.resources.getString(R.string.tv_salary_no_info)
    }

    return resStr
}
