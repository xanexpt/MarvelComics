package com.badjoras.marvel.userinterface.comicdetaisactivity

import com.badjoras.marvel.abstraction.UiContract

class ComicDetailsContract {
    interface View : UiContract.View {
        fun updateComicName(title: String)
    }

    interface Presenter : UiContract.Presenter
    interface Navigator {
        fun close()
    }
}