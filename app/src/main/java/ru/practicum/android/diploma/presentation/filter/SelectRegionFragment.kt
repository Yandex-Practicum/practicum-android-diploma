package ru.practicum.android.diploma.presentation.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.presentation.ui.theme.AppTheme
import ru.practicum.android.diploma.presentation.ui.theme.Blue
import ru.practicum.android.diploma.presentation.ui.theme.Dimens

class SelectRegionFragment : Fragment() {

    private val viewModel by viewModel<SelectRegionViewModel>()

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

                    SelectRegionScreen(
                        state = state,
                        onBackClicked = { findNavController().popBackStack() },
                        onQueryChanged = viewModel::onQueryChanged,
                        onClearQueryClicked = viewModel::onClearQueryClicked,
                        onRegionClicked = ::onRegionClicked,
                    )
                }
            }
        }
    }

    private fun onRegionClicked(region: Region) {
        val bundle = Bundle().apply {
            putString(WorkplaceFragment.REGION_ID_KEY, region.id)
            putString(WorkplaceFragment.REGION_NAME_KEY, region.name)
            putString(WorkplaceFragment.COUNTRY_ID_KEY, region.countryId)
            putString(WorkplaceFragment.COUNTRY_NAME_KEY, region.countryName)
        }
        parentFragmentManager.setFragmentResult(
            WorkplaceFragment.REGION_SELECTION_REQUEST_KEY,
            bundle
        )
        findNavController().popBackStack()
    }
}

@Composable
private fun SelectRegionScreen(
    state: SelectRegionUiState,
    onBackClicked: () -> Unit,
    onQueryChanged: (String) -> Unit,
    onClearQueryClicked: () -> Unit,
    onRegionClicked: (Region) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = Dimens.paddingDefault),
    ) {
        SelectRegionTopBar(onBackClicked = onBackClicked)
        Spacer(modifier = Modifier.height(Dimens.paddingSystemBar))

        val query = when (state) {
            is SelectRegionUiState.Content -> state.query
            is SelectRegionUiState.EmptySearch -> state.query
            else -> ""
        }

        RegionSearchField(
            query = query,
            onQueryChanged = onQueryChanged,
            onClearQueryClicked = onClearQueryClicked,
        )

        Spacer(modifier = Modifier.height(Dimens.paddingDefault))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            when (state) {
                is SelectRegionUiState.Loading -> RegionLoading()
                is SelectRegionUiState.Error -> RegionErrorPlaceholder()
                is SelectRegionUiState.EmptySearch -> RegionEmptySearchPlaceholder()
                is SelectRegionUiState.Content -> RegionList(
                    regions = state.regions,
                    onRegionClicked = onRegionClicked,
                )
            }
        }
    }
}

@Composable
private fun SelectRegionTopBar(onBackClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = Dimens.paddingTopLarge,
                start = 0.dp,
                end = 0.dp,
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
            text = stringResource(R.string.filter_region_title),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
            ),
        )
    }
}

@Composable
private fun RegionSearchField(
    query: String,
    onQueryChanged: (String) -> Unit,
    onClearQueryClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.searchFieldHeight)
            .clip(RoundedCornerShape(Dimens.cornerRadius))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(start = Dimens.paddingDefault, end = Dimens.paddingSystemBar),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChanged,
            modifier = Modifier.weight(1f),
            textStyle = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            ),
            cursorBrush = SolidColor(Blue),
            singleLine = true,
            decorationBox = { innerTextField ->
                if (query.isBlank()) {
                    Text(
                        text = stringResource(R.string.filter_region_hint),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                        ),
                    )
                }
                innerTextField()
            },
        )

        if (query.isBlank()) {
            Box(
                modifier = Modifier.size(Dimens.iconSizeSmall),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_search_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
        } else {
            IconButton(onClick = onClearQueryClicked) {
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
private fun RegionLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(Dimens.searchProgressSize),
            color = Blue,
            strokeWidth = Dimens.paddingSmall,
        )
    }
}

@Composable
private fun RegionErrorPlaceholder() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.il_region_error_328),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )
            Spacer(modifier = Modifier.height(Dimens.paddingDefault))
            Text(
                text = stringResource(R.string.filter_region_error),
                modifier = Modifier.padding(horizontal = Dimens.paddingExtraLarge),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 22.sp),
            )
        }
    }
}

@Composable
private fun RegionEmptySearchPlaceholder() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.il_main_no_results_328),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
            )
            Spacer(modifier = Modifier.height(Dimens.paddingDefault))
            Text(
                text = stringResource(R.string.filter_region_empty),
                modifier = Modifier.padding(horizontal = Dimens.paddingExtraLarge),
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 22.sp),
            )
        }
    }
}

@Composable
private fun RegionList(
    regions: List<Region>,
    onRegionClicked: (Region) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = Dimens.paddingSmall),
    ) {
        items(regions) { region ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.heightMedium)
                    .clickable { onRegionClicked(region) },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = region.name,
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                    ),
                )
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_forward_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}
