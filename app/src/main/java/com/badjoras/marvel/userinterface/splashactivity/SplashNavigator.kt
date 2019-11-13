package com.badjoras.marvel.userinterface.splashactivity

import androidx.appcompat.app.AppCompatActivity
import com.badjoras.marvel.R
import com.badjoras.marvel.userinterface.homeactivity.HomeActivity

class SplashNavigator(private var parentActivity: AppCompatActivity) : SplashContract.Navigator {
    override fun advanceToHome() {
        parentActivity.startActivity(HomeActivity.getNavigationIntent(parentActivity))
        parentActivity.finish()
        parentActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}