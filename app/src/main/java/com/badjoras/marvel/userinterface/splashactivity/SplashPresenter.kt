package com.badjoras.marvel.userinterface.splashactivity

import com.badjoras.marvel.abstraction.BasePresenter
import com.badjoras.marvel.dependencyinjection.modules.SchedulerModule
import io.reactivex.Observable
import io.reactivex.Scheduler
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

class SplashPresenter(
    private var view: SplashContract.View,
    private var navigator: SplashContract.Navigator
) : BasePresenter(), SplashContract.Presenter {

    @Inject
    @field:Named(SchedulerModule.VIEW_SCHEDULER)
    internal lateinit var viewScheduler: Scheduler

    override fun onResume() {
        super.onResume()
        timerToAdvance()
    }

    private fun timerToAdvance() {
        addOnResumeSubscription(Observable.timer(3, TimeUnit.SECONDS)
            .observeOn(viewScheduler)
            .subscribe(
                { navigator.advanceToHome() },
                { error -> Timber.e(error) }
            )
        )
    }
}