package ru.practicum.android.diploma.search.data.network.cache

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.practicum.android.diploma.search.data.network.InternetController
import javax.inject.Inject


class ForceCacheInterceptor@Inject constructor(private val internetController: InternetController) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        if (!internetController.isInternetAvailable()) {
            builder.cacheControl(CacheControl.FORCE_CACHE);
        }
        return chain.proceed(builder.build());
    }
}
