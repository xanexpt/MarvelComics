package com.badjoras.marvel.userinterface.homeactivity

import com.badjoras.marvel.abstraction.BasePresenter
import com.badjoras.marvel.dependencyinjection.modules.SchedulerModule
import com.badjoras.marvel.models.ComicsResponse
import com.badjoras.marvel.services.MarvelServices
import com.badjoras.marvel.userinterface.homeactivity.recycler.PaginationInfoModel
import io.reactivex.Scheduler
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class HomePresenter(
    private var view: HomeContract.View,
    private var service: MarvelServices
) : BasePresenter(), HomeContract.Presenter {

    @Inject
    @field:Named(SchedulerModule.VIEW_SCHEDULER)
    internal lateinit var viewScheduler: Scheduler

    override fun onResume() {
        super.onResume()
        getComics()
        handleImageSelected()
        handleRecyclerPaginationEvent()
    }

    private fun handleRecyclerPaginationEvent() {
        addOnResumeSubscription(view.setupLoadMoreItemsEvent()
            .observeOn(viewScheduler)
            .subscribe(
                {paginationInfo -> loadMorePages(paginationInfo)},
                { error -> Timber.e(error) }
            ))
    }

    private fun loadMorePages(paginationInfo: PaginationInfoModel) {
        addOnResumeSubscription(service.getComicsList(paginationInfo!!.currentAdapterComicListSize)
            .observeOn(viewScheduler)
            .subscribe(
                { response -> handleComicsPagedResponseSuccess(response) },
                { error -> handleComicsResponseError(error) }
            ))
    }

    private fun handleImageSelected() {
        addOnResumeSubscription(view.setupImageSelected()
            .observeOn(viewScheduler)
            .subscribe(
                {},
                { error -> Timber.e(error) }
            ))
    }

    private fun getComics() {
        addOnResumeSubscription(service.getComicsList(0)
            .observeOn(viewScheduler)
            .subscribe(
                { response -> handleComicsResponseSuccess(response) },
                { error -> handleComicsResponseError(error) }
            ))
    }

    private fun handleComicsResponseError(error: Throwable?) {
        Timber.e(error)
    }

    private fun handleComicsResponseSuccess(response: ComicsResponse?) {
        if (response != null) {
            view.prepareRecyclerData(response.data.results)
        }
    }

    private fun handleComicsPagedResponseSuccess(response: ComicsResponse?) {
        if (response != null) {
            view.addMorePagesToRecycler(response.data.results)
        }
    }
}