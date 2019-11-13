package com.badjoras.marvel.userinterface.comicdetaisactivity

import android.os.Bundle
import com.badjoras.marvel.abstraction.BasePresenter
import com.badjoras.marvel.models.Results
import com.badjoras.marvel.models.Thumbnail
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
        prepareImageView(comicDetails)
        prepareComicTitle(comicDetails!!.title)
        prepareComicDescription(comicDetails!!.description)
    }

    private fun prepareImageView(comicData: Results?) {
        if (hasValidThumbnail(comicData!!.thumbnail)) {
            view.setImageURI(getThumbnailUrl(comicData.thumbnail))
        }
    }

    private fun prepareComicDescription(description: String) {
        view.updateDescription(description)
    }

    private fun prepareComicTitle(title: String) {
        view.updateComicName(title)
    }

    private fun getThumbnailUrl(thumbnail: Thumbnail?): String {
        return thumbnail!!.path + "." + thumbnail.extension
    }

    private fun hasValidThumbnail(thumbnail: Thumbnail?): Boolean {
        return (thumbnail != null && !thumbnail.extension.isNullOrEmpty()
                && !thumbnail.path.isNullOrEmpty())
    }
}