package ru.practicum.android.diploma.util.extentions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Salary

@Composable
fun Salary?.formatSalary(): String {
    return formatSalary(
        SalaryFormatLabels(
            fromLabel = stringResource(R.string.from),
            toLabel = stringResource(R.string.to),
            emptyText = stringResource(R.string.no_salary),
        )
    )
}
