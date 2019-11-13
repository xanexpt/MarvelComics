package com.badjoras.marvel.dependencyinjection.components

import com.badjoras.marvel.BaseApplication
import com.badjoras.marvel.dependencyinjection.modules.ApplicationModule
import com.badjoras.marvel.dependencyinjection.scope.ApplicationScope
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(application: BaseApplication)
}