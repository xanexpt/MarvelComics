package com.badjoras.marvel.dependencyinjection.components

import com.badjoras.marvel.BaseApplication
import com.badjoras.marvel.dependencyinjection.modules.*
import com.badjoras.marvel.dependencyinjection.scope.ApplicationScope
import com.badjoras.marvel.services.MarvelServices
import com.badjoras.marvel.userinterface.homeactivity.HomePresenter
import com.badjoras.marvel.userinterface.splashactivity.SplashPresenter
import dagger.Component

@ApplicationScope
@Component(modules = [(MainInjectorModule::class), (ApplicationModule::class),
    (NetworkModule::class), (SchedulerModule::class)])
interface MainInjectorComponent {

    fun inject(application: BaseApplication)

    fun inject(presenter: SplashPresenter)
    fun inject(presenter: HomePresenter)

    fun inject(service: MarvelServices)
}