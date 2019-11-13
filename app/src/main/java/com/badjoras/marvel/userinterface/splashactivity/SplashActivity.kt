package com.badjoras.marvel.userinterface.splashactivity

import android.os.Bundle
import com.badjoras.marvel.BaseApplication
import com.badjoras.marvel.R
import com.badjoras.marvel.abstraction.BaseActivity
import com.badjoras.marvel.abstraction.UiContract

class SplashActivity : BaseActivity(), SplashContract.View {

    override fun getPresenter(): UiContract.Presenter? {
        val mainInjector = (application as BaseApplication).applicationComponent!!

        val navigator = SplashNavigator(this)

        val presenter = SplashPresenter(this, navigator)
        mainInjector.inject(presenter)

        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)
    }

    companion object {
        const val LAYOUT = R.layout.activity_splash
    }
}
