package ru.practicum.android.diploma.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import ru.practicum.android.diploma.ui.common.IconImage
import ru.practicum.android.diploma.ui.common.TopBar
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.Blue
import kotlin.collections.get

@Composable
fun SearchScreen(
    searchText: String = "",
    errorVisible: Boolean = false,
    progressVisible: Boolean = false,
    itemsListVisible: Boolean = false,
//  viewModel: VacancySearchViewModel,
//  onItemClick: (Vacancy) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val isPreview = LocalInspectionMode.current
//  val uiState by viewModel.uiState.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }
    val searchFieldStyle = TextStyle(
        color = MaterialTheme.colorScheme.onBackground,
        fontFamily = FontFamily(Font(R.font.ys_display_regular)),
        fontSize = 19.sp,
        fontWeight = FontWeight(400)
    )
    val isSearchTextEmpty = searchText.isEmpty()

    LaunchedEffect(Unit) {
        if (!isPreview) {
            focusRequester.requestFocus()
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                text = stringResource(R.string.search_screen_title),
                navIconVisible = true,
                endFirstIconVisible = true,
                endSecondIconVisible = true
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            val fieldShape = RoundedCornerShape(8.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                    .height(56.dp)
                    .clip(fieldShape)
                    .background(MaterialTheme.colorScheme.surfaceContainer),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    value = searchText,//uiState.text,
                    onValueChange = { newText ->
                        // onSearchTextChange(newText)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(start = 20.dp)
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
                            if (isSearchTextEmpty) {//(uiState.text.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.search_input_hint),
                                    style = searchFieldStyle.copy(
                                        color = MaterialTheme.colorScheme.inverseOnSurface
                                    ),
                                    maxLines = 1,
                                    modifier = Modifier.align(Alignment.CenterStart),
                                )
                            }
                            innerTextField()
                        }
                    }
                )
                IconImage(
                    resId = if (isSearchTextEmpty) R.drawable.ic_search else R.drawable.ic_cross,
                    modifier = Modifier.padding(end = 4.dp)
                )
            }
            if (progressVisible)//(uiState.progressBarVisible) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(36.dp),
                        color = Blue,
                        strokeWidth = 3.dp,
                    )
                }
        }
        if (itemsListVisible) {//(uiState.recyclerVisible) {
            LazyColumn(
                modifier = Modifier.padding(top = 16.dp)
            ) {
//                    items(uiState.resultList.size) { index ->
//                        ItemCell(
//                            track = uiState.resultList[index],
//                            onClick = { onItemClick(uiState.resultList[index]) }
//                        )
//                    }
            }
        }
        ErrorChip(
            iconId = R.drawable.no_internet_error,//uiState.errorIcon,
            visible = errorVisible, //uiState.errorVisible,
            textId = R.string.no_internet_error //uiState.errorText,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SearchScreenPreview() {
    AppTheme {
        SearchScreen()
    }
}
