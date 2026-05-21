package ru.practicum.android.diploma.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.common.ErrorChip
import ru.practicum.android.diploma.ui.common.TopBar
import ru.practicum.android.diploma.ui.theme.Blue

@Composable
fun SearchScreen(
    viewModel: VacancySearchViewModel,
    onItemClick: (Vacancy) -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val uiState by viewModel.uiState.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }
    val searchFieldStyle = TextStyle(
        color = MaterialTheme.colorScheme.onBackground,
        fontFamily = FontFamily(Font(R.font.ys_display_medium)),
        fontSize = 19.sp,
        fontWeight = FontWeight(400)
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Scaffold(
        topBar = {
            TopBar(
                text = stringResource(R.string.search_screen_title)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            val corner = dimensionResource(R.dimen.search_text_edit_corner_radius)
            val fieldShape = RoundedCornerShape(corner)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                    .height(56.dp)
                    .clip(fieldShape)
                    .background(MaterialTheme.colorScheme.surfaceContainer),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 15.dp)
                )
                Spacer(Modifier.width(8.dp))
                BasicTextField(
                    value = uiState.text,
                    onValueChange = { newText ->
                       onSearchTextChange(newText)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .focusRequester(focusRequester),
                    singleLine = true,
                    textStyle = searchFieldStyle,
                    cursorBrush = SolidColor(Blue),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = { keyboardController?.hide() }
                    ),
                    interactionSource = interactionSource,
                    decorationBox = { innerTextField ->
                        Box(
                            Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (uiState.text.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.search_input_hint),
                                    style = searchFieldStyle.copy(MaterialTheme.colorScheme.inverseOnSurface),
                                    maxLines = 1
                                )
                            }
                            innerTextField()
                        }
                    }
                )
                if (uiState.clearIconVisibility && uiState.text.isNotEmpty()) {
                    Image(
                        painter = painterResource(R.drawable.ic_cross),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                        modifier = Modifier
                            .padding(start = 4.dp, end = 12.dp)
                            .size(12.dp)
                            .clickable {
                                onSearchTextChange("")
                                viewModel.setText("")
                                viewModel.lastSearchText = ""
                                keyboardController?.hide()
                            }
                    )
                }
                    ErrorChip(
                        iconId = uiState.errorIcon,
                        visible = uiState.errorVisible,
                        textId = uiState.errorText,
                    )
            }
        }
    }
}

@Composable
@Preview
fun SearchScreenPreview() = SearchScreen(
    viewModel = VacancySearchViewModel(),
    items = emptyList(),
    onItemClick = {}
)

