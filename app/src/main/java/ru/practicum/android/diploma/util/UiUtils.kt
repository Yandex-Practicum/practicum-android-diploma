package ru.practicum.android.diploma.util

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.util.TypedValue
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import ru.practicum.android.diploma.R
import java.util.Locale

fun dpToPx(dp: Float, context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics
    ).toInt()
}

//Может использоваться для ручной настройки локали
fun getLocalizedContext(base: Context, locale: Locale): Context {
    val config = Configuration(base.resources.configuration)
    config.setLocale(locale)
    return base.createConfigurationContext(config)
}

//общая функция для скрытия клавиатуры
fun EditText.hideKeyboardOnDone(context: Context) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            clearFocus()
            val imm =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
            true
        } else false
    }
}

fun EditText.hideKeyboardOnIconClose(context: Context) {
    val imm =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun Context.getThemeColor(attrResId: Int): Int {
    val typedArray = theme.obtainStyledAttributes(intArrayOf(attrResId))
    val color = typedArray.getColor(0, 0)
    typedArray.recycle()
    return color
}

fun TextInputLayout.renderFilterField(
    context: Context,
    text: String?,
    hintResId: Int,
    grayColor: Int,
    defaultColor: Int = context.getThemeColor(com.google.android.material.R.attr.colorOnPrimary)
) {
    val resolvedText = text.orEmpty()
    val isEmpty = resolvedText.isBlank()

    this.editText?.setText(resolvedText)
    this.hint = context.getString(hintResId)

    val color = if (isEmpty) grayColor else defaultColor
    val hintStyle = if (isEmpty) R.style.HintAppearance_Normal else R.style.HintAppearance_Small
    val icon = if (isEmpty) R.drawable.arrow_forward_24px else R.drawable.close_24px

    this.defaultHintTextColor = ColorStateList.valueOf(color)
    this.setHintTextAppearance(hintStyle)
    this.setEndIconDrawable(icon)

}
