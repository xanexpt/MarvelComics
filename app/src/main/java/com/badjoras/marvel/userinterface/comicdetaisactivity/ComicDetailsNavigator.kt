package com.badjoras.marvel.userinterface.comicdetaisactivity

import androidx.appcompat.app.AppCompatActivity
import com.badjoras.marvel.R
import com.badjoras.marvel.userinterface.homeactivity.HomeActivity

class ComicDetailsNavigator(private var parentActivity: AppCompatActivity) : ComicDetailsContract.Navigator {
    override fun close() {
        parentActivity.finish()
        parentActivity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}