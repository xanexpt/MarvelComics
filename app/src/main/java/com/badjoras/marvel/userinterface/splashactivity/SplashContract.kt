package com.badjoras.marvel.userinterface.splashactivity

import com.badjoras.marvel.abstraction.UiContract

class SplashContract {
    interface View : UiContract.View
    interface Presenter : UiContract.Presenter
    interface Navigator {
        fun advanceToHome()
    }
}