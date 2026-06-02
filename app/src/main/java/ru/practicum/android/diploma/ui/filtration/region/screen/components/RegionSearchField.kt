package ru.practicum.android.diploma.ui.filtration.region.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.common.IconImage
import ru.practicum.android.diploma.ui.filtration.region.action.ChooseRegionAction
import ru.practicum.android.diploma.ui.theme.Blue
import ru.practicum.android.diploma.ui.theme.Dimens

private val SearchFieldHeight = 56.dp
private val SearchFieldCornerRadius = 8.dp
private val SearchFieldTopPadding = 8.dp
private val SearchFieldTextStartPadding = 20.dp
private val SearchFieldIconEndPadding = 4.dp

@Composable
fun RegionSearchField(
    searchQuery: String,
    onAction: (ChooseRegionAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }
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
        BasicTextField(
            value = searchQuery,
            onValueChange = { onAction(ChooseRegionAction.SearchQueryChanged(it)) },
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(start = SearchFieldTextStartPadding),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium
                .copy(color = MaterialTheme.colorScheme.secondaryFixed),
            cursorBrush = SolidColor(Blue),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() },
            ),
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = stringResource(R.string.choose_region_search_hint),
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
        IconImage(
            modifier = Modifier
                .padding(end = SearchFieldIconEndPadding)
                .clickable(
                    enabled = searchQuery.isNotEmpty(),
                    onClick = { onAction(ChooseRegionAction.ClearSearchClicked) },
                ),
            resId = if (searchQuery.isEmpty()) {
                R.drawable.ic_search
            } else {
                R.drawable.ic_cross
            },
            color = MaterialTheme.colorScheme.secondaryFixed,
        )
    }
}
