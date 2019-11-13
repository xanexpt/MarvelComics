package com.badjoras.marvel

import android.app.Application
import com.badjoras.marvel.dependencyinjection.components.DaggerMainInjectorComponent
import com.badjoras.marvel.dependencyinjection.components.MainInjectorComponent
import com.badjoras.marvel.dependencyinjection.modules.ApplicationModule
import com.badjoras.marvel.dependencyinjection.modules.MainInjectorModule
import com.facebook.drawee.backends.pipeline.Fresco
import timber.log.Timber
import com.facebook.common.logging.FLog
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.facebook.imagepipeline.listener.RequestListener
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


class BaseApplication : Application() {
    var applicationComponent: MainInjectorComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        setupDagger()
        setupTimber()
        setupFresco()
        setupFresco()
    }

    private fun setupFresco() {
        if (BuildConfig.DEBUG) {
            val requestListeners = hashSetOf<RequestListener>()
            requestListeners.add(RequestLoggingListener())
            val config = ImagePipelineConfig.newBuilder(this)
                // other setters
                .setRequestListeners(requestListeners)
                .build()
            Fresco.initialize(this, config)
            FLog.setMinimumLoggingLevel(FLog.VERBOSE)
        } else {
            Fresco.initialize(this)
        }
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupDagger() {
        applicationComponent = DaggerMainInjectorComponent.builder()
            .applicationModule(ApplicationModule(this))
            .mainInjectorModule(MainInjectorModule())
            .build()
        applicationComponent!!.inject(this)
    }
}