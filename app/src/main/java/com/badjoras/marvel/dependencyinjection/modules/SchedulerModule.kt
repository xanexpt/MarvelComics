package com.badjoras.marvel.dependencyinjection.modules

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

@Module
class SchedulerModule {

    @Provides
    @Named(IO_SCHEDULER)
    fun provideIoScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @Named(VIEW_SCHEDULER)
    fun provideViewScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    companion object {
        const val VIEW_SCHEDULER = "VIEW_SCHEDULER"
        const val IO_SCHEDULER = "IO_SCHEDULER"
    }
}