package ru.practicum.android.diploma.filter.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.AppTheme
import ru.practicum.android.diploma.core.ui.theme.Dimens
import ru.practicum.android.diploma.core.ui.utils.Spacer
import ru.practicum.android.diploma.core.ui.utils.TextField

@Composable
fun SalaryTextField(
    query: String,
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit,
    onIconClick: (() -> Unit)? = null,
) {
    TextField(
        query = query,
        label = stringResource(R.string.filter_salary_label),
        modifier = modifier,
        onQueryChanged = onQueryChanged,
        onFocusChanged = { },
        trailingIconId = if (query.isEmpty()) {
            null
        } else {
            R.drawable.ic_core_close
        },
        onIconClick = onIconClick,
        contentPaddings = PaddingValues(
            start = Dimens.padding16,
            top = Dimens.padding8,
            end = Dimens.padding16,
            bottom = Dimens.padding8
        ),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        R.string.search_placeholder_sum
    )
}

@Preview(name = "Light", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun SalaryTextFieldPreview() {
    AppTheme {
        Column(modifier = Modifier.padding(Dimens.padding16)) {
            SalaryTextField(
                query = "124",
                onQueryChanged = {},
                onIconClick = { }
            )
            Spacer(height = Dimens.padding16)

            SalaryTextField(
                query = "",
                onQueryChanged = {},
                onIconClick = {}
            )
        }
    }
}
