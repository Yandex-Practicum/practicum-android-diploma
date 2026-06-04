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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.presentation.ui.theme.AppTheme
import ru.practicum.android.diploma.presentation.ui.theme.Blue
import ru.practicum.android.diploma.presentation.ui.theme.Dimens

class SelectCountryFragment : Fragment() {

    private val viewModel by viewModel<SelectCountryViewModel>()

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

                    SelectCountryScreen(
                        state = state,
                        onBackClicked = { findNavController().popBackStack() },
                        onCountryClicked = ::onCountryClicked,
                    )
                }
            }
        }
    }

    private fun onCountryClicked(country: Country) {
        val bundle = Bundle().apply {
            putString(WorkplaceFragment.COUNTRY_ID_KEY, country.id)
            putString(WorkplaceFragment.COUNTRY_NAME_KEY, country.name)
        }
        parentFragmentManager.setFragmentResult(
            WorkplaceFragment.COUNTRY_SELECTION_REQUEST_KEY,
            bundle
        )
        findNavController().popBackStack()
    }
}

@Composable
private fun SelectCountryScreen(
    state: SelectCountryUiState,
    onBackClicked: () -> Unit,
    onCountryClicked: (Country) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        SelectCountryTopBar(onBackClicked = onBackClicked)
        Spacer(modifier = Modifier.height(Dimens.paddingDefault))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = Dimens.paddingDefault),
        ) {
            when (state) {
                is SelectCountryUiState.Loading -> CountryLoading()
                is SelectCountryUiState.Error -> CountryErrorPlaceholder()
                is SelectCountryUiState.Content -> CountryList(
                    countries = state.countries,
                    onCountryClicked = onCountryClicked
                )
            }
        }
    }
}

@Composable
private fun SelectCountryTopBar(onBackClicked: () -> Unit) {
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
            text = stringResource(R.string.filter_country_title),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
            ),
        )
    }
}

@Composable
private fun CountryLoading() {
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
private fun CountryErrorPlaceholder() {
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
private fun CountryList(
    countries: List<Country>,
    onCountryClicked: (Country) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = Dimens.paddingSmall),
    ) {
        items(countries) { country ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.heightMedium)
                    .clickable { onCountryClicked(country) },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = country.name,
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
