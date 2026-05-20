package ru.practicum.android.diploma.util.extentions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Salary

@Composable
fun Salary?.formatSalary(): String {
    if (this == null) return stringResource(R.string.no_salary)

    return buildString {
        from?.let {
            append("${stringResource(R.string.from)} ${it.formatWithSpaces()} ")
        }
        to?.let {
            append(
                "${
                    stringResource(R.string.to)
                } ${it.formatWithSpaces()} "
            )
        }
        append(currency)
    }.trim()
}
