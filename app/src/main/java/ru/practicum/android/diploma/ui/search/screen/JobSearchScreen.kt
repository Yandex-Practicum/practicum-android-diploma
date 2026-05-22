package ru.practicum.android.diploma.ui.search.screen

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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.search.viewmodel.JobSearchViewModel
import ru.practicum.android.diploma.ui.common.IconImage
import ru.practicum.android.diploma.ui.common.TopBar
import ru.practicum.android.diploma.ui.theme.Blue

@Composable
fun JobSearchScreen(
    viewModel: JobSearchViewModel,
    onSearchTextChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val isPreview = LocalInspectionMode.current
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(Unit) {
        if (!isPreview) {
            focusRequester.requestFocus()
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                text = stringResource(R.string.search_screen_title),
                navIconVisible = false,
                endFirstIconVisible = true,
                endFirstIconId = R.drawable.ic_filter,
                endSecondIconVisible = false
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
                    value = searchQuery,
                    onValueChange = { newText ->
                        onSearchTextChange(newText)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(start = 20.dp)
                        .focusRequester(focusRequester),
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium
                        .copy(color = MaterialTheme.colorScheme.onBackground),
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
                            if (searchQuery.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.search_input_hint),
                                    style = MaterialTheme.typography.bodyMedium.copy(
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
                    resId = if (searchQuery.isEmpty()) R.drawable.ic_search else R.drawable.ic_cross,
                    modifier = Modifier.padding(end = 4.dp)
                )
            }
        }
    }
}
