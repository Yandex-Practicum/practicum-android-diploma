package ru.practicum.android.diploma.vacancy.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.Dimens
import ru.practicum.android.diploma.core.ui.utils.HtmlBlock
import ru.practicum.android.diploma.core.ui.utils.HtmlText
import ru.practicum.android.diploma.core.ui.utils.parseHtmlBlocks

@Composable
fun VacancyDescription(
    description: String,
    modifier: Modifier = Modifier
) {
    if (description.isBlank()) return
    val blocks = remember(description) {
        parseHtmlBlocks(description).let { if (it.firstOrNull() is HtmlBlock.Heading) it.drop(1) else it }
    }
    if (blocks.isEmpty()) return

    Column(modifier = modifier.fillMaxWidth().padding(horizontal = Dimens.padding16)) {
        Text(
            text = stringResource(R.string.vacancy_description_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(Dimens.padding16))
        HtmlText(blocks)
    }
}
