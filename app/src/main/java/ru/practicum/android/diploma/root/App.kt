package ru.practicum.android.diploma.root

import android.app.Application
import com.msaggik.featured_jobs.di.featuredJobsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.data.di.dataModule
import ru.practicum.android.diploma.job_search.di.jobSearchModule
import ru.practicum.android.diploma.team.di.teamModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                dataModule,
                jobSearchModule,
                featuredJobsModule,
                teamModule
            )
        }
    }
}
