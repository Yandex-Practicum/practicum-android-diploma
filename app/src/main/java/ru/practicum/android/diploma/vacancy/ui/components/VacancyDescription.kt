package ru.practicum.android.diploma.vacancy.ui.components

import android.graphics.Typeface
import android.text.Spanned
import android.text.style.BulletSpan
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.ui.theme.Dimens

private sealed interface DescriptionBlock {
    val text: AnnotatedString

    data class Heading(override val text: AnnotatedString) : DescriptionBlock
    data class Paragraph(override val text: AnnotatedString) : DescriptionBlock
    data class ListItem(override val text: AnnotatedString) : DescriptionBlock
}

@Composable
fun VacancyDescription(
    description: String,
    modifier: Modifier = Modifier
) {
    if (description.isBlank()) return
    val blocks = remember(description) { parseBlocks(description) }
    if (blocks.isEmpty()) return

    Column(modifier = modifier.fillMaxWidth().padding(horizontal = Dimens.padding16)) {
        Text(
            text = stringResource(R.string.vacancy_description_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(Dimens.padding16))
        blocks.forEachIndexed { index, block ->
            if (index > 0) Spacer(Modifier.height(gapBefore(blocks[index - 1], block)))
            RenderBlock(block)
        }
    }
}

@Composable
private fun RenderBlock(block: DescriptionBlock) {
    val color = MaterialTheme.colorScheme.onBackground
    val body = MaterialTheme.typography.bodyMedium
    when (block) {
        is DescriptionBlock.Heading -> Text(
            text = block.text,
            style = body.copy(fontWeight = FontWeight.Medium),
            color = color
        )
        is DescriptionBlock.Paragraph -> Text(text = block.text, style = body, color = color)
        is DescriptionBlock.ListItem -> Row {
            Text(text = "•  ", style = body, color = color)
            Text(text = block.text, style = body, color = color)
        }
    }
}

private fun gapBefore(prev: DescriptionBlock, next: DescriptionBlock) = when {
    next is DescriptionBlock.Heading -> Dimens.padding16
    prev is DescriptionBlock.Heading -> Dimens.padding4
    prev is DescriptionBlock.ListItem && next is DescriptionBlock.ListItem -> 2.dp
    else -> Dimens.padding8
}

private fun parseBlocks(html: String): List<DescriptionBlock> {
    val spanned = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
    val text = spanned.toString()
    val blocks = mutableListOf<DescriptionBlock>()
    var start = 0
    while (start <= text.length) {
        val end = text.indexOf('\n', start).let { if (it == -1) text.length else it }
        if (end > start) {
            val content = sliceAnnotated(spanned, start, end)
            if (content.text.isNotBlank()) blocks += toBlock(spanned, start, end, content)
        }
        if (end == text.length) break
        start = end + 1
    }
    return blocks
}

private fun toBlock(spanned: Spanned, from: Int, to: Int, content: AnnotatedString): DescriptionBlock = when {
    spanned.getSpans(from, to, RelativeSizeSpan::class.java).isNotEmpty() -> DescriptionBlock.Heading(content)
    spanned.getSpans(from, to, BulletSpan::class.java).isNotEmpty() -> DescriptionBlock.ListItem(content)
    else -> DescriptionBlock.Paragraph(content)
}

private fun sliceAnnotated(spanned: Spanned, from: Int, to: Int): AnnotatedString {
    val start = (from until to).firstOrNull { !spanned[it].isWhitespace() } ?: return AnnotatedString("")
    val end = (to - 1 downTo start).firstOrNull { !spanned[it].isWhitespace() }?.plus(1) ?: start
    return buildAnnotatedString {
        append(spanned.subSequence(start, end).toString())
        var cursor = start
        while (cursor < end) {
            val next = spanned.nextSpanTransition(cursor, end, CharacterStyle::class.java)
            spanned.getSpans(cursor, next, CharacterStyle::class.java).forEach { span ->
                styleFor(span)?.let { addStyle(it, cursor - start, next - start) }
            }
            cursor = next
        }
    }
}

private fun styleFor(span: CharacterStyle): SpanStyle? = when (span) {
    is StyleSpan -> when (span.style) {
        Typeface.BOLD -> SpanStyle(fontWeight = FontWeight.Bold)
        Typeface.ITALIC -> SpanStyle(fontStyle = FontStyle.Italic)
        Typeface.BOLD_ITALIC -> SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)
        else -> null
    }
    is UnderlineSpan -> SpanStyle(textDecoration = TextDecoration.Underline)
    is ForegroundColorSpan -> SpanStyle(color = Color(span.foregroundColor))
    else -> null
}
