package ru.practicum.android.diploma.presentation.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.ui.theme.AppTheme
import ru.practicum.android.diploma.presentation.ui.theme.Blue
import ru.practicum.android.diploma.presentation.ui.theme.Dimens
import ru.practicum.android.diploma.presentation.ui.theme.WhiteUniversal

class WorkplaceFragment : Fragment() {

    private val viewModel by viewModel<WorkplaceViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parentFragmentManager.setFragmentResultListener(
            COUNTRY_SELECTION_REQUEST_KEY,
            this
        ) { _, bundle ->
            val id = bundle.getString(COUNTRY_ID_KEY)
            val name = bundle.getString(COUNTRY_NAME_KEY)
            if (id != null && name != null) {
                viewModel.onCountrySelected(id, name)
            }
        }

        parentFragmentManager.setFragmentResultListener(
            REGION_SELECTION_REQUEST_KEY,
            this
        ) { _, bundle ->
            val regionId = bundle.getString(REGION_ID_KEY)
            val regionName = bundle.getString(REGION_NAME_KEY)
            val countryId = bundle.getString(COUNTRY_ID_KEY)
            val countryName = bundle.getString(COUNTRY_NAME_KEY)
            if (regionId != null && regionName != null) {
                val rId: String = regionId
                val rName: String = regionName
                if (countryId != null && countryName != null) {
                    val cId: String = countryId
                    val cName: String = countryName
                    viewModel.onRegionSelected(rId, rName, cId, cName)
                }
            }
        }
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

                    WorkplaceScreen(
                        state = state,
                        onBackClicked = { findNavController().popBackStack() },
                        onCountryClicked = ::onCountryClicked,
                        onRegionClicked = { onRegionClicked(state.countryId) },
                        onCountryCleared = viewModel::onCountryCleared,
                        onRegionCleared = viewModel::onRegionCleared,
                        onApplyClicked = ::onApplyClicked,
                    )
                }
            }
        }
    }

    private fun onCountryClicked() {
        findNavController().navigate(R.id.selectCountryFragment)
    }

    private fun onRegionClicked(countryId: String?) {
        val bundle = Bundle().apply {
            putString(COUNTRY_ID_KEY, countryId)
        }
        findNavController().navigate(R.id.selectRegionFragment, bundle)
    }

    private fun onApplyClicked() {
        viewModel.onApplyClicked()
        findNavController().popBackStack()
    }

    companion object {
        const val COUNTRY_SELECTION_REQUEST_KEY = "country_selection"
        const val REGION_SELECTION_REQUEST_KEY = "region_selection"
        const val COUNTRY_ID_KEY = "country_id"
        const val COUNTRY_NAME_KEY = "country_name"
        const val REGION_ID_KEY = "region_id"
        const val REGION_NAME_KEY = "region_name"
    }
}

@Composable
private fun WorkplaceScreen(
    state: WorkplaceUiState,
    onBackClicked: () -> Unit,
    onCountryClicked: () -> Unit,
    onRegionClicked: () -> Unit,
    onCountryCleared: () -> Unit,
    onRegionCleared: () -> Unit,
    onApplyClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        WorkplaceTopBar(onBackClicked = onBackClicked)
        Spacer(modifier = Modifier.height(Dimens.paddingDefault))
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = Dimens.paddingDefault),
        ) {
            WorkplaceRow(
                label = stringResource(R.string.filter_country),
                value = state.countryName,
                onRowClick = onCountryClicked,
                onClearClick = onCountryCleared,
            )
            WorkplaceRow(
                label = stringResource(R.string.filter_region),
                value = state.regionName,
                onRowClick = onRegionClicked,
                onClearClick = onRegionCleared,
            )
            Spacer(modifier = Modifier.weight(1f))
            if (state.showApplyButton) {
                Button(
                    onClick = onApplyClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.heightButton)
                        .padding(bottom = Dimens.paddingDefault),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Blue,
                        contentColor = WhiteUniversal,
                    ),
                    shape = RoundedCornerShape(Dimens.cornerRadius),
                ) {
                    Text(
                        text = stringResource(R.string.filter_workplace_apply),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                        ),
                    )
                }
            }
        }
    }
}

@Composable
private fun WorkplaceTopBar(onBackClicked: () -> Unit) {
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
            text = stringResource(R.string.filter_workplace_title),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
            ),
        )
    }
}

@Composable
private fun WorkplaceRow(
    label: String,
    value: String?,
    onRowClick: () -> Unit,
    onClearClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.heightMedium)
            .clickable(onClick = onRowClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            if (value != null) {
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                )
                Text(
                    text = value,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                )
            } else {
                Text(
                    text = label,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                )
            }
        }
        if (value != null) {
            IconButton(onClick = onClearClick) {
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
