package com.badjoras.marvel.userinterface.homeactivity

import androidx.appcompat.app.AppCompatActivity
import com.badjoras.marvel.R
import com.badjoras.marvel.models.Results
import com.badjoras.marvel.userinterface.comicdetaisactivity.ComicDetailsActivity

class HomeNavigator(private var parentActivity: AppCompatActivity) : HomeContract.Navigator{
    override fun openComicDetails(comicDetails: Results) {
        parentActivity.startActivity(ComicDetailsActivity.getNavigationIntent(parentActivity, comicDetails))
        parentActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}