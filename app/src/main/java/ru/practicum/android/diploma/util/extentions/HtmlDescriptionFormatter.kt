package ru.practicum.android.diploma.util.extentions

import android.graphics.Typeface
import android.text.Spanned
import android.text.style.StyleSpan
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.core.text.HtmlCompat
import ru.practicum.android.diploma.presentation.vacancy.model.VacancyDescriptionElement

private val BLOCK_TAG_PATTERN = Regex(
    pattern = "<(p|ul|ol|li)\\b[^>]*>",
    options = setOf(RegexOption.IGNORE_CASE),
)

private val STRONG_ONLY_PATTERN = Regex(
    pattern = "^\\s*<(?:strong|b)[^>]*>(.+?)</(?:strong|b)>\\s*$",
    options = setOf(RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL),
)

private val LIST_ITEM_PATTERN = Regex(
    pattern = "<li[^>]*>(.*?)</li>",
    options = setOf(RegexOption.IGNORE_CASE, RegexOption.DOT_MATCHES_ALL),
)

fun String?.formatHtmlDescription(): String {
    if (isNullOrBlank()) {
        return ""
    }
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim()
}

fun String.toVacancyDescriptionElements(sectionTitleToStrip: String): List<VacancyDescriptionElement> {
    if (isBlank()) {
        return emptyList()
    }

    val preparedHtml = stripLeadingDescriptionSectionHtml(sectionTitleToStrip)
    val tokens = tokenizeDescriptionHtml(preparedHtml)
    if (tokens.isEmpty()) {
        return listOf(VacancyDescriptionElement.Paragraph(htmlFragmentToAnnotatedString(preparedHtml)))
    }

    return tokens.toDescriptionElements()
        .dropLeadingSectionTitleElement(sectionTitleToStrip)
}

private sealed interface DescriptionToken {
    data class Paragraph(val html: String) : DescriptionToken

    data class ListItem(val html: String) : DescriptionToken
}

private fun String.stripLeadingDescriptionSectionHtml(sectionTitle: String): String {
    val escapedTitle = Regex.escape(sectionTitle)
    val patterns = listOf(
        Regex(
            "<(?:strong|b|h[1-6])[^>]*>\\s*$escapedTitle\\s*</(?:strong|b|h[1-6])>\\s*",
            RegexOption.IGNORE_CASE,
        ),
        Regex(
            "<p>\\s*<(?:strong|b)[^>]*>\\s*$escapedTitle\\s*</(?:strong|b)>\\s*</p>\\s*",
            RegexOption.IGNORE_CASE,
        ),
    )
    var result = this
    patterns.forEach { pattern ->
        result = result.replaceFirst(pattern, "")
    }
    return result.trim()
}

private fun tokenizeDescriptionHtml(html: String): List<DescriptionToken> {
    val tokens = mutableListOf<DescriptionToken>()
    var searchStart = 0

    while (searchStart < html.length) {
        val match = BLOCK_TAG_PATTERN.find(html, searchStart) ?: break
        val tag = match.groupValues[1].lowercase()
        val contentStart = match.range.last + 1
        val closeTag = "</$tag>"
        val closeIndex = html.indexOf(closeTag, contentStart, ignoreCase = true)
        if (closeIndex == -1) {
            searchStart = match.range.last + 1
            continue
        }

        val innerHtml = html.substring(contentStart, closeIndex)
        when (tag) {
            "p" -> tokens.add(DescriptionToken.Paragraph(innerHtml))
            "ul", "ol" -> {
                LIST_ITEM_PATTERN.findAll(innerHtml).forEach { listItem ->
                    tokens.add(DescriptionToken.ListItem(listItem.groupValues[1]))
                }
            }
            "li" -> tokens.add(DescriptionToken.ListItem(innerHtml))
        }
        searchStart = closeIndex + closeTag.length
    }

    return tokens
}

private fun List<DescriptionToken>.toDescriptionElements(): List<VacancyDescriptionElement> {
    val elements = mutableListOf<VacancyDescriptionElement>()
    var afterSubHeader = false

    forEach { token ->
        when (token) {
            is DescriptionToken.Paragraph -> {
                if (token.html.isStrongOnlyParagraph()) {
                    afterSubHeader = true
                    elements.add(
                        VacancyDescriptionElement.SubHeader(
                            text = token.html.extractStrongText().formatHtmlDescription(),
                        ),
                    )
                } else if (afterSubHeader) {
                    elements.add(
                        VacancyDescriptionElement.Bullet(
                            text = htmlFragmentToAnnotatedString(token.html),
                        ),
                    )
                } else {
                    afterSubHeader = false
                    elements.add(
                        VacancyDescriptionElement.Paragraph(
                            text = htmlFragmentToAnnotatedString(token.html),
                        ),
                    )
                }
            }
            is DescriptionToken.ListItem -> {
                afterSubHeader = true
                elements.add(
                    VacancyDescriptionElement.Bullet(
                        text = htmlFragmentToAnnotatedString(token.html),
                    ),
                )
            }
        }
    }

    return elements.filter { it.isNotBlank() }
}

private fun String.isStrongOnlyParagraph(): Boolean {
    return STRONG_ONLY_PATTERN.matches(this)
}

private fun String.extractStrongText(): String {
    return STRONG_ONLY_PATTERN.find(this)?.groupValues?.get(1).orEmpty()
}

private fun htmlFragmentToAnnotatedString(html: String): AnnotatedString {
    if (html.isBlank()) {
        return AnnotatedString("")
    }
    val spanned = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
    return spanned.toAnnotatedStringWithSubHeaders().trimLeadingBlankLines()
}

private fun Spanned.toAnnotatedStringWithSubHeaders(): AnnotatedString {
    return buildAnnotatedString {
        append(this@toAnnotatedStringWithSubHeaders)
        getSpans(0, length, StyleSpan::class.java).forEach { span ->
            if (span.style == Typeface.BOLD || span.style == Typeface.BOLD_ITALIC) {
                addStyle(
                    SpanStyle(fontWeight = FontWeight.Medium),
                    getSpanStart(span),
                    getSpanEnd(span),
                )
            }
        }
    }
}

private fun VacancyDescriptionElement.isNotBlank(): Boolean {
    return when (this) {
        is VacancyDescriptionElement.SubHeader -> text.isNotBlank()
        is VacancyDescriptionElement.Paragraph -> text.text.isNotBlank()
        is VacancyDescriptionElement.Bullet -> text.text.isNotBlank()
    }
}

private fun List<VacancyDescriptionElement>.dropLeadingSectionTitleElement(
    sectionTitle: String,
): List<VacancyDescriptionElement> {
    val first = firstOrNull() ?: return this
    if (first is VacancyDescriptionElement.SubHeader &&
        first.text.equals(sectionTitle, ignoreCase = true)
    ) {
        return drop(1)
    }
    if (first is VacancyDescriptionElement.Paragraph &&
        first.text.text.startsWith(sectionTitle, ignoreCase = true)
    ) {
        val trimmedText = first.text.text
            .removePrefix(sectionTitle)
            .trimStart('\n', ' ', '\t')
        if (trimmedText.isBlank()) {
            return drop(1)
        }
        return listOf(VacancyDescriptionElement.Paragraph(AnnotatedString(trimmedText))) + drop(1)
    }
    return this
}

private fun AnnotatedString.trimLeadingBlankLines(): AnnotatedString {
    val trimmedStart = text.indexOfFirst { !it.isWhitespace() }.let { index ->
        if (index < 0) text.length else index
    }
    if (trimmedStart == 0) {
        return this
    }
    return dropLeadingChars(trimmedStart)
}

private fun AnnotatedString.dropLeadingChars(count: Int): AnnotatedString {
    if (count <= 0) {
        return this
    }
    if (count >= text.length) {
        return AnnotatedString("")
    }
    val newText = text.substring(count)
    return buildAnnotatedString {
        append(newText)
        spanStyles.forEach { range ->
            if (range.end > count) {
                addStyle(
                    range.item,
                    (range.start - count).coerceAtLeast(0),
                    range.end - count,
                )
            }
        }
    }
}
