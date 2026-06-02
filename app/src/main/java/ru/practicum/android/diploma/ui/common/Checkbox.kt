package ru.practicum.android.diploma.ui.common

import android.widget.CheckBox
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun CheckBox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Checkbox(
        enabled = true,
        checked = isChecked,
        onCheckedChange = onCheckedChange,
        colors = CheckboxColors(
            checkedCheckmarkColor = MaterialTheme.colorScheme.background,
            uncheckedCheckmarkColor = MaterialTheme.colorScheme.background,
            checkedBoxColor = MaterialTheme.colorScheme.background,
            uncheckedBoxColor = MaterialTheme.colorScheme.primary,
            disabledCheckedBoxColor = MaterialTheme.colorScheme.primary,
            disabledUncheckedBoxColor = MaterialTheme.colorScheme.primary,
            disabledIndeterminateBoxColor = MaterialTheme.colorScheme.primary,
            checkedBorderColor = MaterialTheme.colorScheme.primary,
            uncheckedBorderColor = MaterialTheme.colorScheme.primary,
            disabledBorderColor = MaterialTheme.colorScheme.primary,
            disabledUncheckedBorderColor = MaterialTheme.colorScheme.primary,
            disabledIndeterminateBorderColor = MaterialTheme.colorScheme.primary
        )
    )
}
