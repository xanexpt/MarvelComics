package com.badjoras.marvel.userinterface.homeactivity

import com.badjoras.marvel.abstraction.UiContract
import com.badjoras.marvel.models.HomeImageModelHelper
import com.badjoras.marvel.models.Results
import com.badjoras.marvel.userinterface.homeactivity.recycler.PaginationInfoModel
import io.reactivex.Observable

class HomeContract {
    interface View : UiContract.View {
        fun prepareRecyclerData(results: List<Results>)
        fun setupImageSelected(): Observable<HomeImageModelHelper>
        fun setupLoadMoreItemsEvent(): Observable<PaginationInfoModel>
        fun setupDetailsSelected(): Observable<Results>
        fun addMorePagesToRecycler(results: List<Results>)
    }

    interface Presenter : UiContract.Presenter
    interface Navigator {
        fun openComicDetails(comicDetails:Results)
    }
}