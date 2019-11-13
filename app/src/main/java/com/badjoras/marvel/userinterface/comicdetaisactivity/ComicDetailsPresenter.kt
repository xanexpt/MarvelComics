package com.badjoras.marvel.userinterface.comicdetaisactivity

import android.os.Bundle
import com.badjoras.marvel.abstraction.BasePresenter
import com.badjoras.marvel.models.Results
import com.badjoras.marvel.services.MarvelServices
import com.google.gson.Gson

class ComicDetailsPresenter(
    private var view: ComicDetailsContract.View,
    private var navigator: ComicDetailsContract.Navigator,
    private var service: MarvelServices
) : BasePresenter(), ComicDetailsContract.Presenter {

    private var comicDetails: Results?=null
    val gson = Gson()

    override fun setInitialState(state: Bundle?) {
        super.setInitialState(state)
        if (state != null && state.containsKey(ComicDetailsActivity.EXTRA_COMIC_ID)) {
            val json:String = state.getString(ComicDetailsActivity.EXTRA_COMIC_ID, "")
            comicDetails = gson.fromJson(json, Results::class.java)
        }
    }

    override fun getCurrentState(): Bundle {
        val state = Bundle()
        state.putString(ComicDetailsActivity.EXTRA_COMIC_ID, gson.toJson(comicDetails))
        return state
    }

    override fun onResume() {
        super.onResume()
        prepareScreenIfNeeded()
    }

    private fun prepareScreenIfNeeded() {
        putComicTitle(comicDetails!!.title)
    }

    private fun putComicTitle(title: String) {
        view.updateComicName(title)
    }
}