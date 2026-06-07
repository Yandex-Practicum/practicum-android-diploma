@file:Suppress("LongMethod")

package ru.practicum.android.diploma.presentation.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.FilterSettings
import ru.practicum.android.diploma.presentation.ui.theme.AppTheme
import ru.practicum.android.diploma.presentation.ui.theme.Blue
import ru.practicum.android.diploma.presentation.ui.theme.Dimens
import ru.practicum.android.diploma.presentation.ui.theme.Red
import ru.practicum.android.diploma.presentation.ui.theme.WhiteUniversal

class FilterFragment : Fragment() {

    private val viewModel by viewModel<FilterViewModel>()

    override fun onResume() {
        super.onResume()
        viewModel.loadFilterSettings()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                AppTheme {
                    val state by viewModel.uiState.collectAsState()

                    FilterScreen(
                        state = state,
                        onBackClicked = { findNavController().popBackStack() },
                        onWorkplaceClicked = { findNavController().navigate(R.id.workplaceFragment) },
                        onSalaryChanged = viewModel::onSalaryChanged,
                        onSalaryClearClicked = viewModel::onSalaryClearClicked,
                        onOnlyWithSalaryChanged = viewModel::onOnlyWithSalaryChanged,
                        onApplyClicked = ::onApplyClicked,
                        onResetClicked = viewModel::onResetClicked,
                        onWorkplaceClearClicked = viewModel::onWorkplaceClearClicked,
                    )
                }
            }
        }
    }

    private fun onApplyClicked() {
        parentFragmentManager.setFragmentResult(FILTER_APPLIED_REQUEST_KEY, Bundle.EMPTY)
        findNavController().popBackStack()
    }

    companion object {
        const val FILTER_APPLIED_REQUEST_KEY = "filterApplied"
    }
}

@Composable
private fun FilterScreen(
    state: FilterUiState,
    onBackClicked: () -> Unit,
    onWorkplaceClicked: () -> Unit,
    onSalaryChanged: (String) -> Unit,
    onSalaryClearClicked: () -> Unit,
    onOnlyWithSalaryChanged: (Boolean) -> Unit,
    onApplyClicked: () -> Unit,
    onResetClicked: () -> Unit,
    onWorkplaceClearClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        FilterTopBar(onBackClicked = onBackClicked)
        Spacer(modifier = Modifier.height(Dimens.paddingDefault))
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = Dimens.paddingDefault),
        ) {
            FilterNavigationRow(
                text = state.workplaceTitle ?: stringResource(R.string.filter_workplace),
                isSelected = state.workplaceTitle != null,
                onClick = onWorkplaceClicked,
                onClearClick = onWorkplaceClearClicked,
            )
            FilterNavigationRow(
                text = state.industryTitle ?: stringResource(R.string.filter_industry),
                isSelected = state.industryTitle != null,
                onClick = {},
            )
            Spacer(modifier = Modifier.height(Dimens.paddingDefault))
            SalaryField(
                salary = state.settings.salary,
                onSalaryChanged = onSalaryChanged,
                onSalaryClearClicked = onSalaryClearClicked,
            )
            Spacer(modifier = Modifier.height(Dimens.paddingTopLarge))
            OnlyWithSalaryRow(
                checked = state.settings.onlyWithSalary,
                onCheckedChange = onOnlyWithSalaryChanged,
            )
            Spacer(modifier = Modifier.weight(1f))
            if (state.shouldShowApplyButton) {
                FilterActions(
                    showResetButton = state.shouldShowResetButton,
                    onApplyClicked = onApplyClicked,
                    onResetClicked = onResetClicked,
                )
            }
        }
    }
}

@Composable
private fun FilterTopBar(onBackClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = Dimens.paddingTopLarge,
                start = Dimens.paddingSmall,
                end = Dimens.paddingDefault,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = onBackClicked) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
        Spacer(modifier = Modifier.width(Dimens.paddingSystemBar))
        Text(
            text = stringResource(R.string.filter_title),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
            ),
        )
    }
}

@Composable
private fun FilterNavigationRow(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    onClearClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.heightMedium)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            modifier = Modifier.weight(1f),
            color = if (isSelected) {
                MaterialTheme.colorScheme.onBackground
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            ),
        )
        if (isSelected && onClearClick != null) {
            IconButton(
                onClick = onClearClick,
                modifier = Modifier.offset(x = 12.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_close_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        } else {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_forward_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Composable
private fun SalaryField(
    salary: String,
    onSalaryChanged: (String) -> Unit,
    onSalaryClearClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.heightMenuItem)
            .clip(RoundedCornerShape(Dimens.cornerRadius))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(horizontal = Dimens.paddingDefault),
    ) {
        Text(
            text = stringResource(R.string.filter_salary),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 8.dp),
            color = if (salary.isNotBlank()) {
                Blue
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
            ),
        )

        BasicTextField(
            value = salary,
            onValueChange = onSalaryChanged,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(top = 28.dp, end = Dimens.iconSizeMedium),
            textStyle = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            cursorBrush = SolidColor(Blue),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box {
                    if (salary.isBlank()) {
                        Text(
                            text = stringResource(R.string.filter_salary_hint),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                            ),
                        )
                    }
                    innerTextField()
                }
            },
        )

        if (salary.isNotBlank()) {
            IconButton(
                onClick = onSalaryClearClicked,
                modifier = Modifier.align(Alignment.CenterEnd),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_close_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}

@Composable
private fun OnlyWithSalaryRow(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.filter_hide_no_salary),
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            ),
        )
        Icon(
            painter = painterResource(
                if (checked) R.drawable.ic_check_box_on_24 else R.drawable.ic_check_box_off_24
            ),
            contentDescription = null,
            tint = Blue,
        )
    }
}

@Composable
private fun FilterActions(
    showResetButton: Boolean,
    onApplyClicked: () -> Unit,
    onResetClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = Dimens.paddingDefault),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = onApplyClicked,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.heightButton),
            colors = ButtonDefaults.buttonColors(
                containerColor = Blue,
                contentColor = WhiteUniversal,
            ),
            shape = RoundedCornerShape(Dimens.cornerRadius),
        ) {
            Text(
                text = stringResource(R.string.filter_apply),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                ),
            )
        }
        if (showResetButton) {
            TextButton(onClick = onResetClicked) {
                Text(
                    text = stringResource(R.string.filter_reset),
                    color = Red,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                    ),
                )
            }
        }
    }
}

@Preview(
    name = "Filter empty",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
)
@Composable
private fun FilterEmptyPreview() {
    AppTheme {
        FilterScreen(
            state = FilterUiState(),
            onBackClicked = {},
            onWorkplaceClicked = {},
            onSalaryChanged = {},
            onSalaryClearClicked = {},
            onOnlyWithSalaryChanged = {},
            onApplyClicked = {},
            onResetClicked = {},
            onWorkplaceClearClicked = {},
        )
    }
}

@Preview(
    name = "Filter filled",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
)
@Composable
private fun FilterFilledPreview() {
    AppTheme {
        FilterScreen(
            state = FilterUiState(
                settings = FilterSettings(
                    salary = "100000",
                    onlyWithSalary = true,
                )
            ),
            onBackClicked = {},
            onWorkplaceClicked = {},
            onSalaryChanged = {},
            onSalaryClearClicked = {},
            onOnlyWithSalaryChanged = {},
            onApplyClicked = {},
            onResetClicked = {},
            onWorkplaceClearClicked = {},
        )
    }
}
