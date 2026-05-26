package ru.practicum.android.diploma.util.extentions

import ru.practicum.android.diploma.domain.models.Salary

fun Salary?.formatSalary(labels: SalaryFormatLabels): String {
    if (this == null || (from == null && to == null)) {
        return labels.emptyText
    }

    return buildString {
        from?.let { append("${labels.fromLabel} ${it.formatWithSpaces()} ") }
        to?.let { append("${labels.toLabel} ${it.formatWithSpaces()} ") }
        append(currency.toCurrencySymbol())
    }.trim()
}
