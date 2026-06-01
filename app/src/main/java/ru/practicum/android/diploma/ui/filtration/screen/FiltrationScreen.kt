package ru.practicum.android.diploma.ui.filtration.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.common.IconImage
import ru.practicum.android.diploma.ui.common.TopBar
import ru.practicum.android.diploma.ui.search.screen.SearchScreenTestTags
import ru.practicum.android.diploma.ui.theme.Blue
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun FiltrationScreen(
    country: String,
    specialization: String,
    expectedSalary: String,
    dontShowWithoutSalaryChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onClear: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        topBar = {
            TopBar(
                text = stringResource(R.string.filter_settings_title),
                navIconVisible = true,
                endFirstIconVisible = false,
                endSecondIconVisible = false
            )
        },
        contentWindowInsets = WindowInsets(bottom = 0),
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            CellComponent(
                upperText = stringResource(R.string.filter_settings_country_title),
                lowerText = country,
                trailingIconId = R.drawable.ic_arrow_right
            )
            CellComponent(
                upperText = stringResource(R.string.specialization_title),
                lowerText = specialization,
                trailingIconId = R.drawable.ic_arrow_right
            )
           SalaryTextEdit(
               searchQuery = expectedSalary,
               interactionSource = interactionSource,
               onSearchTextChange = onSearchTextChange,
               onClear = onClear,
               onKeyboardDone = { keyboardController?.hide() }
           )
            DontShowWithoutSalary(
                dontShowWithoutSalaryChecked,
                onCheckedChange
            )
        }
    }
}

@Composable
fun DontShowWithoutSalary(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f)
                .align(Alignment.CenterVertically),
            text = stringResource(R.string.do_not_show_without_salary_title),
            style = MaterialTheme.typography.titleMedium
        )
        Box(
            modifier = Modifier
                .height(Dimens.TopBarIconSize)
                .width(Dimens.TopBarIconSize)
                .align(Alignment.CenterVertically),
            contentAlignment = Alignment.Center,
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
    }
}

@Composable
fun SalaryTextEdit(
    searchQuery: String,
    interactionSource: MutableInteractionSource,
    onSearchTextChange: (String) -> Unit,
    onClear: () -> Unit,
    onKeyboardDone: () -> Unit,
) {
    val fieldShape = RoundedCornerShape(8.dp)
    val isFocused by interactionSource.collectIsFocusedAsState()
    val labelColor = if ( isFocused) {
        MaterialTheme.colorScheme.primary
    } else {
        if( searchQuery.isEmpty()) {
            MaterialTheme.colorScheme.inverseOnSurface
        } else {
            MaterialTheme.colorScheme.secondaryFixed
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Dimens.ScreenHorizontalPadding,
                top = 24.dp,
                end = Dimens.ScreenHorizontalPadding,
                bottom = 24.dp
            )
            .height(56.dp)
            .clip(fieldShape)
            .background(MaterialTheme.colorScheme.surfaceContainer),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = searchQuery,
            onValueChange = onSearchTextChange,
            modifier = Modifier
                .weight(1f)
                .testTag(SearchScreenTestTags.TextField)
                .fillMaxHeight(),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium
                .copy(color = MaterialTheme.colorScheme.secondaryFixed),
            cursorBrush = SolidColor(Blue),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { onKeyboardDone() }),
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column(
                        modifier = Modifier.padding(start = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(0.dp),
                    ) {
                        Text(
                            modifier = Modifier.padding(top = 8.dp),
                            text = stringResource(R.string.filter_settings_salary_title),
                            style = MaterialTheme.typography.bodySmall.copy(
                                lineHeight = 12.sp,
                                platformStyle = PlatformTextStyle(includeFontPadding = false),
                                lineHeightStyle = LineHeightStyle(
                                    alignment = LineHeightStyle.Alignment.Top,
                                    trim = LineHeightStyle.Trim.Both,
                                ),
                            ),
                            color = labelColor,
                        )
                        Box(contentAlignment = Alignment.CenterStart) {
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.input_amount_hint),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.inverseOnSurface,
                                        lineHeight = 16.sp,
                                        platformStyle = PlatformTextStyle(includeFontPadding = false),
                                        lineHeightStyle = LineHeightStyle(
                                            alignment = LineHeightStyle.Alignment.Top,
                                            trim = LineHeightStyle.Trim.Both,
                                        ),
                                    ),
                                    maxLines = 1,
                                )
                            }
                            innerTextField()
                        }
                    }
                }
            }
        )
        IconImage(
            modifier = Modifier
                .testTag(SearchScreenTestTags.ClearButton)
                .padding(end = 4.dp)
                .clickable(enabled = true, onClick = onClear),
            resId = R.drawable.ic_cross,
            color = MaterialTheme.colorScheme.secondaryFixed
        )
    }
}

@Composable
fun CellComponent(
    upperText: String,
    lowerText: String,
    trailingIconId: Int,
    modifier: Modifier = Modifier,
    onIconClick: () -> Unit = {},
 ) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = upperText,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground
             )
            Text(
                text = lowerText,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        IconImage(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .testTag(SearchScreenTestTags.ClearButton)
                .padding(end = 4.dp)
                .clickable(enabled = true, onClick = onIconClick),
            resId = if (lowerText.isEmpty()) trailingIconId else R.drawable.ic_cross,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
@Preview
fun PreviewFiltrationScreen() {
    FiltrationScreen(
        country = "Россия",
        specialization = "Разработчик",
        expectedSalary = "100000",
        true,
        {},
        {},
        {}
    )
}

