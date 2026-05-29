package ru.practicum.android.diploma.core.ui.utils

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.AppTheme
import ru.practicum.android.diploma.core.ui.theme.BlackUniversal
import ru.practicum.android.diploma.core.ui.theme.Blue
import ru.practicum.android.diploma.core.ui.theme.Dimens

@Composable
fun TextField(
    query: String,
    label: String? = null,
    modifier: Modifier = Modifier,
    onQueryChanged: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
    @DrawableRes trailingIconId: Int? = null,
    onIconClick: (() -> Unit)? = null,
    contentPaddings: PaddingValues = PaddingValues(
        start = Dimens.padding16,
        top = Dimens.padding8,
        end = Dimens.padding16,
        bottom = Dimens.padding8
    ),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    val isFocused = remember { false }
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.radius12))
            .background(MaterialTheme.colorScheme.outline),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .defaultMinSize(minHeight = 40.dp)
                .wrapContentHeight()
                .padding(contentPaddings),

        ) {
            label?.let {
                Text(
                    text = label,
                    color = if (query.isEmpty()) Color.Gray else if (isFocused) Blue else BlackUniversal,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            BasicTextField(
                // can set custom contentPadding only in BasicTextField
                value = query,
                onValueChange = onQueryChanged,
                cursorBrush = SolidColor(Blue),
                modifier = Modifier
                    .wrapContentHeight()
                    .onFocusChanged { focusState ->
                        onFocusChanged(focusState.isFocused)
                    },
                keyboardOptions = keyboardOptions,
                decorationBox = @Composable { innerTextField ->
//
                    TextFieldDefaults.DecorationBox(
                        value = query,
                        visualTransformation = VisualTransformation.None,
                        innerTextField = innerTextField,

                        placeholder = { Text(text = stringResource(id = R.string.search_placeholder)) },
                        prefix = null,
                        suffix = null,
                        supportingText = null,
                        // shape = RoundedCornerShape(Dimens.radius12),
                        singleLine = true,
                        enabled = true,
                        isError = false,
                        interactionSource = remember { MutableInteractionSource() },
                        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
                        colors = textFieldColors(MaterialTheme.colorScheme)
                    )
                },
            )
        }

        trailingIconId?.let {
            IconButton(
                onClick = {
                    onIconClick?.invoke()
                },

            ) {
                Icon(
                    painter = painterResource(trailingIconId),
                    contentDescription = null,
                    tint = BlackUniversal,
                    modifier = Modifier.size(Dimens.icon24)
                )
            }
        }
    }
}

@Composable
private fun textFieldColors(colorScheme: ColorScheme): TextFieldColors {
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

@Preview(name = "Light", showBackground = true)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun TextFieldPreview() {
    AppTheme {
        Column {
            TextField(
                label = "Label",
                query = "124",
                modifier = Modifier.padding(Dimens.padding16),
                onQueryChanged = {},
                onFocusChanged = {},
                trailingIconId = R.drawable.ic_search_close
            )

            TextField(
                label = "Label",
                query = "",
                modifier = Modifier.padding(Dimens.padding16),
                onQueryChanged = {},
                onFocusChanged = {}
            )

            TextField(
                query = "124",
                modifier = Modifier.padding(Dimens.padding16),
                onQueryChanged = {},
                onFocusChanged = {},
                trailingIconId = R.drawable.ic_search_close

            )

            TextField(
                query = "",
                modifier = Modifier.padding(Dimens.padding16),
                onQueryChanged = {},
                onFocusChanged = {},
                trailingIconId = R.drawable.ic_search_close
            )
        }
    }
}
