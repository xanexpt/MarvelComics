package com.badjoras.marvel.userinterface.comicdetaisactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.badjoras.marvel.BaseApplication
import com.badjoras.marvel.R
import com.badjoras.marvel.abstraction.BaseActivity
import com.badjoras.marvel.abstraction.UiContract
import com.badjoras.marvel.models.Results
import com.badjoras.marvel.services.MarvelServices
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_comic_details.*

class ComicDetailsActivity : BaseActivity(), ComicDetailsContract.View {

    override fun getPresenter(): UiContract.Presenter? {
        val mainInjector = (application as BaseApplication).applicationComponent!!

        val navigator = ComicDetailsNavigator(this)

        val service = MarvelServices()
        mainInjector.inject(service)

        val presenter = ComicDetailsPresenter(this, navigator, service)
        mainInjector.inject(presenter)

        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
    }

    override fun updateComicName(title: String) {
        detailsName.text = title
    }

    companion object {
        fun getNavigationIntent(parentActivity: AppCompatActivity, comicDetails: Results): Intent {
            val intent = Intent(parentActivity, ComicDetailsActivity::class.java)
            intent.putExtra(EXTRA_COMIC_ID, Gson().toJson(comicDetails))
            return intent
        }

        private const val LAYOUT = R.layout.activity_comic_details
        private const val CLICK_THROTTLE_DURATION_IN_MILLIS = 500L
        const val EXTRA_COMIC_ID = "extra_id"
    }
}