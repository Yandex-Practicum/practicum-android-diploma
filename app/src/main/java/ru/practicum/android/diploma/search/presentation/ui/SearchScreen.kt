package ru.practicum.android.diploma.search.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.presentation.ui.components.VacanciesList
import ru.practicum.android.diploma.core.presentation.ui.theme.dp16
import ru.practicum.android.diploma.core.presentation.ui.theme.dp20
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchState
import ru.practicum.android.diploma.search.presentation.viewmodel.SearchTextFieldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onOpenVacancyDetails: () -> Unit,
    onOpenFilters: () -> Unit,
    searchState: SearchState,
    textFieldState: SearchTextFieldState
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Поиск",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 19.dp),
                    maxLines = 1,
                )
            }, actions = {
                Icon(
                    painter = painterResource(R.drawable.filter_off__24px),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = dp16, top = dp20, bottom = dp20, end = dp20)
                        .size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            })
        },
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            SearchInput(
                textFieldState.query,
                textFieldState.isShowClearIc,
                onQueryChange = {},
                onClearIcClick = {},
                onVacancyClickDebounce = {}
            )

            VacanciesList(emptyList()) { }
        }
    }
}

@Composable
private fun SearchInput(
    query: String,
    showClearIc: Boolean,
    onClearIcClick: () -> Unit,
    onQueryChange: (String) -> Unit,
    onVacancyClickDebounce: (VacancyDetail) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Box() {
            TextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .fillMaxWidth(),

                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.search_textField_hint),
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.search_24px),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    selectionColors = TextSelectionColors(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primary
                    )
                ),
                textStyle = TextStyle(
                    fontWeight = MaterialTheme.typography.bodyMedium.fontWeight,
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    color = MaterialTheme.colorScheme.onSurface
                ),
            )

            if (showClearIc) {
                IconButton(
                    onClick = onClearIcClick,
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.close_24px),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground

                    )
                }
            }
        }
    }
}
