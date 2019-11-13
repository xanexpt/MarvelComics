package com.badjoras.marvel.userinterface.homeactivity

import com.badjoras.marvel.abstraction.UiContract
import com.badjoras.marvel.models.HomeImageModelHelper
import com.badjoras.marvel.models.Results
import io.reactivex.Observable

class HomeContract {
    interface View : UiContract.View {
        fun prepareRecyclerData(results: List<Results>)
        fun setupImageSelected(): Observable<HomeImageModelHelper>
    }

    interface Presenter : UiContract.Presenter
    interface Navigator
}