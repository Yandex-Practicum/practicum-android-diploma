package ru.practicum.android.diploma.search.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.AppTheme
import ru.practicum.android.diploma.core.ui.theme.BlackUniversal
import ru.practicum.android.diploma.core.ui.theme.Blue
import ru.practicum.android.diploma.core.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    showClearButton: Boolean,
    onIconClicked: () -> Unit
) {
    Column {
        BasicTextField( // can set custom contentPadding only in BasicTextField
            value = query,
            onValueChange = onQueryChanged,
            cursorBrush = SolidColor(Blue),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(all = Dimens.padding16)
                .onFocusChanged { focusState ->
                    onFocusChanged(focusState.isFocused)
                },
            decorationBox = @Composable { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = query,
                    visualTransformation = VisualTransformation.None,
                    innerTextField = innerTextField,
                    placeholder = { Text(text = stringResource(id = R.string.search_placeholder)) },
                    label = null,
                    trailingIcon =
                    {
                        IconButton(
                            onClick = onIconClicked,

                        ) {
                            Icon(
                                painter = painterResource(
                                    id = if (showClearButton) {
                                        R.drawable.ic_search_close
                                    } else {
                                        R.drawable.ic_search_lens
                                    }
                                ),
                                contentDescription = null,
                                tint = BlackUniversal,
                                modifier = Modifier.size(Dimens.icon24)
                            )
                        }

                    },
                    prefix = null,
                    suffix = null,
                    supportingText = null,
                    shape = RoundedCornerShape(Dimens.radius12),
                    singleLine = true,
                    enabled = true,
                    isError = false,
                    interactionSource = remember { MutableInteractionSource() },
                    contentPadding = PaddingValues(horizontal = Dimens.padding16, vertical = Dimens.padding16),
                    colors = searchbarColors(MaterialTheme.colorScheme)
                )
            },
        )

    }
}

@Composable
private fun searchbarColors(colorScheme: ColorScheme): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedTextColor = BlackUniversal,
        unfocusedTextColor = BlackUniversal,
        focusedContainerColor = MaterialTheme.colorScheme.outline,
        unfocusedContainerColor = MaterialTheme.colorScheme.outline,
        cursorColor = Blue,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedLeadingIconColor = BlackUniversal,
        unfocusedLeadingIconColor = BlackUniversal,
        focusedTrailingIconColor = BlackUniversal,
        unfocusedTrailingIconColor = BlackUniversal,
        focusedPlaceholderColor = colorScheme.onSurfaceVariant,
        unfocusedPlaceholderColor = colorScheme.onSurfaceVariant,
    )
}
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun SearchScreenPreview() {
    AppTheme {
        Column {
            SearchBar(
                query = "124",
                onQueryChanged = {},
                onFocusChanged = {},
                showClearButton = true,
                onIconClicked = { }
            )

            SearchBar(
                query = "",
                onQueryChanged = {},
                onFocusChanged = {},
                showClearButton = false,
                onIconClicked = {}
            )
        }
    }
}
