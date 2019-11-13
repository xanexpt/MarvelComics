package com.badjoras.marvel.dependencyinjection.modules

import com.badjoras.marvel.BaseApplication
import com.badjoras.marvel.dependencyinjection.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val baseApplication: BaseApplication) {
    @ApplicationScope
    @Provides
    fun provideBaseApplication(): BaseApplication {
        return baseApplication
    }
}