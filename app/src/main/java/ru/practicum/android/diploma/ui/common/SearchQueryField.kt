package ru.practicum.android.diploma.ui.common

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.Dimens

private val SearchFieldHeight = 56.dp
private val SearchFieldCornerRadius = 8.dp
private val SearchFieldTopPadding = 8.dp
private val SearchFieldTextStartPadding = 20.dp

@Composable
fun SearchQueryField(
    searchQuery: String,
    @StringRes hintRes: Int,
    interactionSource: MutableInteractionSource,
    onSearchTextChange: (String) -> Unit,
    onClear: () -> Unit,
    onKeyboardDone: () -> Unit,
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
) {
    val fieldShape = RoundedCornerShape(SearchFieldCornerRadius)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = Dimens.ScreenHorizontalPadding,
                top = SearchFieldTopPadding,
                end = Dimens.ScreenHorizontalPadding,
            )
            .height(SearchFieldHeight)
            .clip(fieldShape)
            .background(MaterialTheme.colorScheme.surfaceContainer),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextEdit(
            value = searchQuery,
            onValueChange = onSearchTextChange,
            modifier = textFieldModifier
                .weight(1f)
                .fillMaxHeight()
                .padding(start = SearchFieldTextStartPadding),
            interactionSource = interactionSource,
            onKeyboardDone = onKeyboardDone,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = stringResource(hintRes),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.inverseOnSurface,
                            ),
                            maxLines = 1,
                        )
                    }
                    innerTextField()
                }
            },
        )
        TextEditTrailingIcon(
            if (searchQuery.isEmpty()) R.drawable.ic_search else R.drawable.ic_cross,
            onClear,
        )
    }
}
