package com.badjoras.marvel.services

import com.badjoras.marvel.api.MarvelApi
import com.badjoras.marvel.dependencyinjection.modules.SchedulerModule
import com.badjoras.marvel.models.ComicsResponse
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class MarvelServices {

    @Inject
    internal lateinit var api: MarvelApi

    @Inject
    @field:Named(SchedulerModule.IO_SCHEDULER)
    internal lateinit var ioScheduler: Scheduler

    fun getComicsList(offset: Int): Observable<ComicsResponse> {
        return api
            .getComics(offset, ORDER_RECENT)
            .subscribeOn(ioScheduler)
    }

    companion object {
        const val ORDER_RECENT = "-focDate"
    }
}