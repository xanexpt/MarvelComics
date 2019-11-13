package com.badjoras.marvel.dependencyinjection.components

import com.badjoras.marvel.dependencyinjection.modules.SchedulerModule
import dagger.Component
import io.reactivex.Scheduler
import javax.inject.Named

@Component(modules = [(SchedulerModule::class)])
interface SchedulerComponent {

    @Named(SchedulerModule.IO_SCHEDULER)
    fun ioScheduler(): Scheduler

    @Named(SchedulerModule.VIEW_SCHEDULER)
    fun viewScheduler(): Scheduler
}