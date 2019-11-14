package com.badjoras.marvel.userinterface.comicdetaisactivity

import com.badjoras.marvel.abstraction.UiContract
import io.reactivex.Observable

class ComicDetailsContract {
    interface View : UiContract.View {
        fun updateComicName(title: String)
        fun updateDescription(description: String)
        fun setImageURI(thumbnailUrl: String)
        fun setupCloseSelected(): Observable<Any>
    }

    interface Presenter : UiContract.Presenter
    interface Navigator {
        fun close()
    }
}