package ru.practicum.android.diploma.presentation.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.presentation.ui.components.vacancy.formatSalary
import ru.practicum.android.diploma.presentation.ui.theme.AppTheme
import ru.practicum.android.diploma.presentation.ui.theme.Blue
import ru.practicum.android.diploma.presentation.ui.theme.Dimens
import ru.practicum.android.diploma.presentation.ui.theme.LightGray
import androidx.core.net.toUri
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.BulletSpan
import android.text.style.StyleSpan
import android.text.style.RelativeSizeSpan
import android.graphics.Canvas
import android.graphics.Paint
import android.text.Layout
import android.text.style.LeadingMarginSpan

class VacancyDetailsFragment : Fragment() {

    private val viewModel by viewModel<VacancyDetailsViewModel>()

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

                    VacancyDetailsScreen(
                        state = state,
                        onBackClicked = { findNavController().popBackStack() },
                        onShareClicked = ::shareVacancy,
                        onEmailClicked = ::sendEmail,
                        onPhoneClicked = ::dialPhone,
                        onRetryClicked = { viewModel.loadVacancyDetails() }
                    )
                }
            }
        }
    }

    private fun shareVacancy(url: String) {
        val finalUrl = url.ifBlank {
            val vacancyId = requireArguments().getString("vacancyId").orEmpty()
            "https://android-diploma.education-services.ru/vacancies/$vacancyId"
        }
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, finalUrl)
        }
        startActivity(Intent.createChooser(shareIntent, null))
    }

    private fun sendEmail(email: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = "mailto:".toUri()
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        }
        try {
            startActivity(Intent.createChooser(emailIntent, null))
        } catch (_: Exception) {
        }
    }

    private fun dialPhone(phoneNumber: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL).apply {
            data = "tel:$phoneNumber".toUri()
        }
        try {
            startActivity(dialIntent)
        } catch (_: Exception) {
        }
    }
}

@Composable
private fun VacancyDetailsScreen(
    state: VacancyDetailsUiState,
    onBackClicked: () -> Unit,
    onShareClicked: (String) -> Unit,
    onEmailClicked: (String) -> Unit,
    onPhoneClicked: (String) -> Unit,
    onRetryClicked: () -> Unit,
) {
    var isFavorite by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        VacancyDetailsHeader(
            isFavorite = isFavorite,
            onBackClicked = onBackClicked,
            onShareClicked = {
                if (state is VacancyDetailsUiState.Success) {
                    onShareClicked(state.vacancy.url)
                }
            },
            onFavoriteClicked = {
                isFavorite = !isFavorite
                // TODO: реализовать сохранение/удаление из избранного в БД через ViewModel/Interactor
            },
            showActions = state is VacancyDetailsUiState.Success
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            when (state) {
                is VacancyDetailsUiState.Loading -> {
                    CentralLoader()
                }
                is VacancyDetailsUiState.Success -> {
                    VacancyDetailsContent(
                        vacancy = state.vacancy,
                        onEmailClicked = onEmailClicked,
                        onPhoneClicked = onPhoneClicked
                    )
                }
                is VacancyDetailsUiState.Error -> {
                    ErrorPlaceholder(
                        errorType = state.errorType,
                        onRetryClicked = onRetryClicked
                    )
                }
            }
        }
    }
}

@Composable
private fun VacancyDetailsHeader(
    isFavorite: Boolean,
    onBackClicked: () -> Unit,
    onShareClicked: () -> Unit,
    onFavoriteClicked: () -> Unit,
    showActions: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClicked) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Text(
            text = stringResource(R.string.vacancy_title),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            ),
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp)
        )

        if (showActions) {
            IconButton(onClick = onShareClicked) {
                Icon(
                    painter = painterResource(R.drawable.ic_sharing_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(onClick = onFavoriteClicked) {
                Icon(
                    painter = painterResource(
                        if (isFavorite) R.drawable.ic_favorites_on_24 else R.drawable.ic_favorites_off_24
                    ),
                    contentDescription = null,
                    tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

@Composable
private fun CentralLoader() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(Dimens.searchProgressSize),
            color = Blue,
            strokeWidth = Dimens.paddingSmall
        )
    }
}

@Composable
private fun VacancyDetailsContent(
    vacancy: VacancyDetail,
    onEmailClicked: (String) -> Unit,
    onPhoneClicked: (String) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = Dimens.paddingDefault, vertical = Dimens.paddingDefault)
    ) {
        Text(
            text = vacancy.name,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 38.sp
            ),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = vacancy.salary.formatSalary(),
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 26.sp
            ),
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(24.dp))

        EmployerCard(vacancy = vacancy)

        Spacer(modifier = Modifier.height(24.dp))

        ExperienceAndScheduleBlock(vacancy = vacancy)

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(R.string.vacancy_description),
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 26.sp
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
        val startsWithHeading = remember(vacancy.description) {
            doesHtmlStartWithHeading(vacancy.description)
        }
        val descriptionSpacerHeight = if (startsWithHeading) 16.dp else 4.dp
        Spacer(modifier = Modifier.height(descriptionSpacerHeight))
        HtmlText(html = vacancy.description)

        if (vacancy.skills.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.vacancy_key_skills),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 26.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            val skillsHtml = remember(vacancy.skills) {
                "<ul>" + vacancy.skills.joinToString("") { "<li>$it</li>" } + "</ul>"
            }
            HtmlText(html = skillsHtml)
        }

        val contacts = vacancy.contacts
        val hasContacts = contacts != null && (
            !contacts.name.isNullOrBlank() ||
                !contacts.email.isNullOrBlank() ||
                contacts.phones.isNotEmpty()
            )
        if (hasContacts) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.vacancy_contacts),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (!contacts.name.isNullOrBlank()) {
                Text(
                    text = stringResource(R.string.vacancy_contact_person),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = contacts.name,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (!contacts.email.isNullOrBlank()) {
                Text(
                    text = stringResource(R.string.vacancy_contact_email),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = contacts.email,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                    color = Blue,
                    modifier = Modifier.clickable { onEmailClicked(contacts.email) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (contacts.phones.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.vacancy_contact_phone),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                    color = MaterialTheme.colorScheme.onBackground
                )
                contacts.phones.forEach { phone ->
                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                        Text(
                            text = phone.formatted,
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp, fontWeight = FontWeight.Medium),
                            color = Blue,
                            modifier = Modifier.clickable { onPhoneClicked(phone.formatted) }
                        )
                        if (!phone.comment.isNullOrBlank()) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = phone.comment,
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmployerCard(vacancy: VacancyDetail) {
    val employer = vacancy.employer
    val context = LocalContext.current
    val logoShape = RoundedCornerShape(Dimens.vacancyLogoCornerRadius)
    
    val addressText = vacancy.address?.let { address ->
        val parts = listOfNotNull(
            address.city,
            address.street,
            address.building
        ).filter(String::isNotBlank)
        if (parts.isNotEmpty()) parts.joinToString(", ") else vacancy.areaName
    } ?: vacancy.areaName

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Dimens.cornerRadius))
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(logoShape)
                .background(Color.White)
                .border(
                    width = Dimens.vacancyLogoBorderWidth,
                    color = LightGray,
                    shape = logoShape
                ),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(employer?.logo)
                    .setHeader("User-Agent", "practicum-android-diploma")
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                contentScale = ContentScale.Fit,
                placeholder = painterResource(R.drawable.ic_placeholder_32),
                error = painterResource(R.drawable.ic_placeholder_32),
                fallback = painterResource(R.drawable.ic_placeholder_32)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = employer?.name.orEmpty(),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 26.sp
                ),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            if (!addressText.isNullOrBlank()) {
                Text(
                    text = addressText,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp,
                        lineHeight = 19.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun ExperienceAndScheduleBlock(vacancy: VacancyDetail) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (vacancy.experience != null && !vacancy.experience.name.isNullOrBlank()) {
            Text(
                text = stringResource(R.string.vacancy_experience),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = vacancy.experience.name,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        val employmentText = listOfNotNull(
            vacancy.employment?.name,
            vacancy.schedule?.name
        ).filter(String::isNotBlank).joinToString(", ")

        if (employmentText.isNotBlank()) {
            Text(
                text = employmentText,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
private fun HtmlText(html: String) {
    val context = LocalContext.current
    val textColor = MaterialTheme.colorScheme.onBackground
    val textColorInt = android.graphics.Color.argb(
        (textColor.alpha * 255).toInt(),
        (textColor.red * 255).toInt(),
        (textColor.green * 255).toInt(),
        (textColor.blue * 255).toInt()
    )

    val fontFamilyResolver = LocalFontFamilyResolver.current
    val typeface = fontFamilyResolver.resolve(MaterialTheme.typography.bodyLarge.fontFamily).value as android.graphics.Typeface

    val cleanedHtml = remember(html) {
        val regex = Regex("^\\s*(<p>)?\\s*(<strong>|<h3>|<h2>)\\s*Описание вакансии\\s*(</strong>|</h3>|</h2>)\\s*(</p>)?\\s*(<br\\s*/?>)?", RegexOption.IGNORE_CASE)
        html.replace(regex, "")
    }

    val spannedText = remember(cleanedHtml, context) {
        applyCustomHtmlSpacing(context, cleanedHtml)
    }

    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { ctx ->
            TextView(ctx).apply {
                textSize = 16f
                setTextColor(textColorInt)
                setTypeface(typeface)
                setLineSpacing(0f, 1.15f)
            }
        },
        update = { textView ->
            textView.text = spannedText
        }
    )
}

private fun applyCustomHtmlSpacing(context: android.content.Context, html: String): Spanned {
    val spanned = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
    val ssb = SpannableStringBuilder(spanned)
    
    val relativeSizeSpans = ssb.getSpans(0, ssb.length, RelativeSizeSpan::class.java)
    for (span in relativeSizeSpans) {
        ssb.removeSpan(span)
    }

    val bulletRadius = dpToPx(context, 3)
    val leftMargin = dpToPx(context, 8)
    val gapWidth = dpToPx(context, 10)

    var i = 0
    while (i < ssb.length) {
        val lineStart = i
        var lineEnd = ssb.indexOf('\n', i)
        if (lineEnd == -1) {
            lineEnd = ssb.length
        }
        
        val standardBullets = ssb.getSpans(lineStart, lineEnd, BulletSpan::class.java)
        var isListItem = standardBullets.isNotEmpty()
        
        for (bullet in standardBullets) {
            ssb.removeSpan(bullet)
        }
        
        var textStart = lineStart
        while (textStart < lineEnd && (ssb[textStart] == ' ' || ssb[textStart] == '\t' || ssb[textStart] == '\u00A0')) {
            textStart++
        }
        
        if (textStart < lineEnd && (ssb[textStart] == '•' || ssb[textStart] == '\u2022' || ssb[textStart] == '\u00B7' || ssb[textStart] == '·')) {
            isListItem = true
            var textAfterBullet = textStart + 1
            while (textAfterBullet < lineEnd && (ssb[textAfterBullet] == ' ' || ssb[textAfterBullet] == '\t' || ssb[textAfterBullet] == '\u00A0')) {
                textAfterBullet++
            }
            ssb.delete(textStart, textAfterBullet)
            lineEnd -= (textAfterBullet - textStart)
        }
        
        if (isListItem && lineStart < lineEnd) {
            ssb.setSpan(
                BeautifulBulletSpan(leftMargin, gapWidth, bulletRadius),
                lineStart,
                lineEnd,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        
        i = lineEnd + 1
    }

    var start = 0
    while (start < ssb.length) {
        var end = ssb.indexOf('\n', start)
        if (end == -1) {
            end = ssb.length
        }

        if (end < ssb.length) {
            val isHeading = isLineHeading(ssb, start, end)
            val isListItem = isLineListItem(ssb, start, end)

            if (isHeading) {
                val nextStart = end + 1
                var nextEnd = ssb.indexOf('\n', nextStart)
                if (nextEnd == -1) {
                    nextEnd = ssb.length
                }
                val isNextHeading = nextStart < ssb.length && isLineHeading(ssb, nextStart, nextEnd)
                
                if (!isNextHeading) {
                    if (end + 1 < ssb.length && ssb[end + 1] == '\n') {
                        ssb.delete(end + 1, end + 2)
                    }
                    ssb.setSpan(AbsoluteSizeSpan(8, true), end, end + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            } else if (isListItem) {
                var nextListItemStart = end + 1
                if (nextListItemStart < ssb.length && ssb[nextListItemStart] == '\n') {
                    nextListItemStart++
                }
                
                val nextListItemSpans = if (nextListItemStart < ssb.length) {
                    var nextListItemEnd = ssb.indexOf('\n', nextListItemStart)
                    if (nextListItemEnd == -1) {
                        nextListItemEnd = ssb.length
                    }
                    ssb.getSpans(nextListItemStart, nextListItemEnd, BeautifulBulletSpan::class.java)
                } else {
                    emptyArray()
                }
                
                val isNextListItem = nextListItemSpans.isNotEmpty()
                
                if (isNextListItem) {
                    if (end + 1 < ssb.length && ssb[end + 1] == '\n') {
                        ssb.delete(end + 1, end + 2)
                    }
                    ssb.setSpan(AbsoluteSizeSpan(1, true), end, end + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
        }

        start = end + 1
    }
    return ssb.trim()
}

private fun SpannableStringBuilder.trim(): SpannableStringBuilder {
    var start = 0
    var end = length

    while (start < end && Character.isWhitespace(this[start])) {
        start++
    }

    while (end > start && Character.isWhitespace(this[end - 1])) {
        end--
    }

    if (start > 0 || end < length) {
        val trimmed = subSequence(start, end)
        clear()
        append(trimmed)
    }
    return this
}

private fun isLineHeading(spanned: Spanned, start: Int, end: Int): Boolean {
    if (start >= end) return false
    val styleSpans = spanned.getSpans(start, end, StyleSpan::class.java)
    for (span in styleSpans) {
        if (span.style == android.graphics.Typeface.BOLD) {
            val spanStart = spanned.getSpanStart(span)
            val spanEnd = spanned.getSpanEnd(span)
            if (spanStart <= start + 1 && spanEnd >= end - 1) {
                return true
            }
        }
    }
    return false
}

private fun isLineListItem(spanned: Spanned, start: Int, end: Int): Boolean {
    if (start >= end) return false
    val bulletSpans = spanned.getSpans(start, end, BeautifulBulletSpan::class.java)
    return bulletSpans.isNotEmpty()
}

private fun dpToPx(context: android.content.Context, dp: Int): Int {
    return (dp * context.resources.displayMetrics.density).toInt()
}

private fun doesHtmlStartWithHeading(html: String): Boolean {
    val regex = Regex("^\\s*(<p>)?\\s*(<strong>|<h3>|<h2>)\\s*Описание вакансии\\s*(</strong>|</h3>|</h2>)\\s*(</p>)?\\s*(<br\\s*/?>)?", RegexOption.IGNORE_CASE)
    val cleaned = html.replace(regex, "").trim()
    return cleaned.startsWith("<strong>", ignoreCase = true) ||
           cleaned.startsWith("<h3>", ignoreCase = true) ||
           cleaned.startsWith("<h2>", ignoreCase = true) ||
           cleaned.startsWith("<b>", ignoreCase = true)
}

private class BeautifulBulletSpan(
    private val leftMarginPx: Int,
    private val gapWidthPx: Int,
    private val bulletRadiusPx: Int
) : LeadingMarginSpan {

    override fun getLeadingMargin(first: Boolean): Int {
        return leftMarginPx + gapWidthPx + (bulletRadiusPx * 2)
    }

    override fun drawLeadingMargin(
        canvas: Canvas,
        paint: Paint,
        x: Int,
        dir: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        first: Boolean,
        layout: Layout
    ) {
        val isFirstLine = (text as? Spanned)?.getSpanStart(this) == start
        if (!isFirstLine) return

        val style = paint.style
        paint.style = Paint.Style.FILL

        val bulletX = x + dir * (leftMarginPx + bulletRadiusPx)
        val bulletY = (top + bottom) / 2f

        canvas.drawCircle(bulletX.toFloat(), bulletY, bulletRadiusPx.toFloat(), paint)
        paint.style = style
    }
}

@Composable
private fun ErrorPlaceholder(
    errorType: VacancyDetailsUiState.ErrorType,
    onRetryClicked: () -> Unit,
) {
    val illustrationRes = when (errorType) {
        VacancyDetailsUiState.ErrorType.NO_INTERNET -> R.drawable.il_main_no_internet_328
        VacancyDetailsUiState.ErrorType.SERVER_ERROR -> R.drawable.il_vacancy_error_server_328
        VacancyDetailsUiState.ErrorType.NOT_FOUND -> R.drawable.il_vacancy_not_found_328
    }

    val textRes = when (errorType) {
        VacancyDetailsUiState.ErrorType.NO_INTERNET -> R.string.search_error_no_internet
        VacancyDetailsUiState.ErrorType.SERVER_ERROR -> R.string.vacancy_error_server
        VacancyDetailsUiState.ErrorType.NOT_FOUND -> R.string.vacancy_not_found
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Image(
                painter = painterResource(illustrationRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(200.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(textRes),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            if (errorType != VacancyDetailsUiState.ErrorType.NOT_FOUND) {
                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(Dimens.cornerRadius))
                        .background(Blue)
                        .clickable { onRetryClicked() }
                        .padding(horizontal = 24.dp, vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Повторить",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Preview(
    name = "Details success",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
)
@Composable
private fun DetailsSuccessPreview() {
    AppTheme {
        VacancyDetailsScreen(
            state = VacancyDetailsUiState.Success(
                VacancyDetail(
                    id = "0001db2d-1366-379d-98ce-6f965bbc7816",
                    name = "Frontend-разработчик",
                    description = "<h2>Описание вакансии</h2><p>Ищем frontend-разработчика для работы над пользовательскими кабинетами и публичными интерфейсами.</p><p>Нужен инженер, который одинаково внимательно относится к UX, производительности и поддерживаемости кода.</p><section><h3>Обязанности</h3><ul><li>Разрабатывать интерфейсы на React и TypeScript.</li><li>Поддерживать дизайн-систему и повторно используемые компоненты.</li><li>Оптимизировать загрузку страниц и стабильность клиентской части.</li></ul></section><section><h3>Требования</h3><ul><li>Уверенное знание JavaScript или TypeScript.</li><li>Опыт работы с React, HTML и CSS.</li><li>Понимание адаптивной верстки, accessibility и REST API.</li></ul></section><section><h3>Условия</h3><ul><li>Прямое взаимодействие с дизайнерами и продуктом.</li><li>Регулярный пересмотр технических решений и места для рефакторинга.</li><li>Гибкий график и современный стек разработки.</li></ul></section>",
                    salary = ru.practicum.android.diploma.domain.models.VacancySalary(from = 1800, to = 3200, currency = "BYR"),
                    address = ru.practicum.android.diploma.domain.models.VacancyAddress(city = "Барнаул", street = "Ленина", building = "16", raw = "Барнаул, Ленина, 16"),
                    experience = ru.practicum.android.diploma.domain.models.VacancyExperience(id = "between1And3", name = "От 1 года до 3 лет"),
                    schedule = ru.practicum.android.diploma.domain.models.VacancySchedule(id = "remote", name = "Удаленная работа"),
                    employment = ru.practicum.android.diploma.domain.models.VacancyEmployment(id = "full", name = "Полная занятость"),
                    contacts = ru.practicum.android.diploma.domain.models.VacancyContacts(
                        name = "Смирнов Алексей Иванович",
                        email = "123@gmail.com",
                        phones = listOf(
                            ru.practicum.android.diploma.domain.models.VacancyPhone(comment = null, formatted = "+7 (999) 456-78-90"),
                            ru.practicum.android.diploma.domain.models.VacancyPhone(comment = null, formatted = "+7 (999) 654-32-10")
                        )
                    ),
                    employer = ru.practicum.android.diploma.domain.models.VacancyEmployer(name = "NVIDIA", logo = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a4/NVIDIA_logo.svg/500px-NVIDIA_logo.svg.png"),
                    areaName = "Барнаул",
                    skills = listOf("TypeScript", "React", "HTML", "CSS", "REST API"),
                    url = "https://android-diploma.education-services.ru/vacancies/0001db2d-1366-379d-98ce-6f965bbc7816"
                )
            ),
            onBackClicked = {},
            onShareClicked = {},
            onEmailClicked = {},
            onPhoneClicked = {},
            onRetryClicked = {}
        )
    }
}

@Preview(
    name = "Details loading",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
)
@Composable
private fun DetailsLoadingPreview() {
    AppTheme {
        VacancyDetailsScreen(
            state = VacancyDetailsUiState.Loading,
            onBackClicked = {},
            onShareClicked = {},
            onEmailClicked = {},
            onPhoneClicked = {},
            onRetryClicked = {}
        )
    }
}

@Preview(
    name = "Details error no internet",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
)
@Composable
private fun DetailsErrorNoInternetPreview() {
    AppTheme {
        VacancyDetailsScreen(
            state = VacancyDetailsUiState.Error(VacancyDetailsUiState.ErrorType.NO_INTERNET),
            onBackClicked = {},
            onShareClicked = {},
            onEmailClicked = {},
            onPhoneClicked = {},
            onRetryClicked = {}
        )
    }
}
