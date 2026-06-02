package ru.practicum.android.diploma.ui.filtration.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filtration.action.FiltrationAction
import ru.practicum.android.diploma.ui.common.CheckBox
import ru.practicum.android.diploma.ui.common.FilterItem
import ru.practicum.android.diploma.ui.common.PrimaryButton
import ru.practicum.android.diploma.ui.common.SecondaryButton
import ru.practicum.android.diploma.ui.common.TextEdit
import ru.practicum.android.diploma.ui.common.TextEditTrailingIcon
import ru.practicum.android.diploma.ui.common.TopBar
import ru.practicum.android.diploma.ui.search.screen.SearchScreenTestTags
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun FiltrationScreen(
    country: String,
    industry: String,
    salary: String,
    dontShowWithoutSalaryChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onSearchTextChange: (String) -> Unit,
    onClear: () -> Unit,
    onApplyClick: () -> Unit,
    onCancelClick: () -> Unit,
    onNavClick: () -> Unit,
    onIndustryClick: () -> Unit,
    onAreaClick: () -> Unit,
    onIndustryClear: () -> Unit,
    onAreaClear: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        topBar = {
            TopBar(
                text = stringResource(R.string.filter_settings_title),
                navIconVisible = true,
                endFirstIconVisible = false,
                endSecondIconVisible = false,
                onNavClick = onNavClick
            )
        },
        bottomBar = {
            FiltrationBottomBar(
                onApplyClick = onApplyClick,
                onCancelClick = onCancelClick,
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            FilterItem(
                modifier = Modifier.padding(top = 16.dp),
                title = stringResource(R.string.filter_settings_country_title),
                value = country.takeIf { it.isNotEmpty() },
                onItemClick = onAreaClick,
                onCrossClick = onAreaClear
            )
            FilterItem(
                title = stringResource(R.string.specialization_title),
                value = industry.takeIf { it.isNotEmpty() },
                onItemClick = onIndustryClick,
                onCrossClick = onIndustryClear
            )
            SalaryTextEdit(
                searchQuery = salary,
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
private fun FiltrationBottomBar(
    onApplyClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(bottom = Dimens.ScreenContentBottomPadding),
    ) {
        PrimaryButton(
            text = stringResource(R.string.apply_button_text),
            onClick = onApplyClick,
        )
        SecondaryButton(
            text = stringResource(R.string.cancel_button_text),
            onClick = onCancelClick,
        )
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
            CheckBox(
                isChecked,
                onCheckedChange
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
    val labelColor = if (isFocused) {
        MaterialTheme.colorScheme.primary
    } else {
        if (searchQuery.isEmpty()) {
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
        TextEdit(
            value = searchQuery,
            onValueChange = onSearchTextChange,
            modifier = Modifier
                .weight(1f)
                .testTag(SearchScreenTestTags.TextField)
                .fillMaxHeight(),
            interactionSource = interactionSource,
            onKeyboardDone = onKeyboardDone,
            decorationBox = { innerTextField ->
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column(
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(0.dp),
                    ) {
                        SalaryTextEditTitle(labelColor)
                        if (searchQuery.isEmpty()) {
                            SalaryTextEditPlaceholder()
                        }
                        innerTextField()
                    }
                }
            },
        )
        TextEditTrailingIcon(
            R.drawable.ic_cross,
            onClear
        )
    }
}

@Composable
fun SalaryTextEditTitle(
    color: Color
) {
    Text(
        text = stringResource(R.string.filter_settings_salary_title),
        style = MaterialTheme.typography.bodySmall.copy(
            lineHeight = 16.sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false),
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Top,
                trim = LineHeightStyle.Trim.Both,
            ),
        ),
        color = color,
    )
}

@Composable
fun SalaryTextEditPlaceholder() {
    Text(
        text = stringResource(R.string.input_amount_hint),
        style = MaterialTheme.typography.bodyMedium.copy(
            color = MaterialTheme.colorScheme.inverseOnSurface,
            lineHeight = 19.sp,
            platformStyle = PlatformTextStyle(includeFontPadding = false),
            lineHeightStyle = LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Top,
                trim = LineHeightStyle.Trim.Both,
            ),
        ),
        maxLines = 1,
    )
}
