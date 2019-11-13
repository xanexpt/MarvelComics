package com.badjoras.marvel.abstraction

import android.content.Intent
import android.os.Bundle

class UiContract {
    interface View
    interface Presenter {
        fun getCurrentState(): Bundle
        fun setInitialState(state: Bundle?)

        fun onNewIntent(intent: Intent?)

        fun onResume()
        fun onPause()

        fun onStart()
        fun onStop()
    }

    interface Navigator
}