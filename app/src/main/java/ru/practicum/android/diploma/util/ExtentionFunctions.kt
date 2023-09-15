package ru.practicum.android.diploma.util

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val DELAY_600_MILLIS = 600L
const val DELAY_2000_MILLIS = 2000L

val <T> T.thisName: String
    get() = this!!::class.simpleName ?: "Unknown class"

fun <T> delayedAction(
    delayMillis: Long = DELAY_2000_MILLIS,
    coroutineScope: CoroutineScope,
    deferredUsing: Boolean = true,
    action: (T) -> Unit,
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        if (deferredUsing) {
            debounceJob?.cancel()
        }
        if (debounceJob?.isCompleted != false || deferredUsing) {
            debounceJob = coroutineScope.launch {
                Log.d("delayedAction", "delayedAction: job = $debounceJob   query = $param", )
                delay(delayMillis)
                action(param)
            }
        }
    }
}

fun ImageView.setImage(url: String, placeholder: Int, cornerRadius: Int) {
    Glide
        .with(this.context)
        .load(url)
        .placeholder(placeholder)
        .transform(CenterInside(), RoundedCorners(cornerRadius))
        .into(this)
}
fun ImageView.setImage(url: String, placeholder: Int) {
    Glide
        .with(this.context)
        .load(url)
        .placeholder(placeholder)
        .transform(CenterCrop())
        .into(this)
}

fun <T : Parcelable?> Bundle.getParcelableFromBundle(key: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        getParcelable(key, clazz)
    else {
        @Suppress("DEPRECATION")
        getParcelable<T>(key)
    }
}