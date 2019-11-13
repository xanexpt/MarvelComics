package com.badjoras.marvel.userinterface.homeactivity.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.badjoras.marvel.R
import com.badjoras.marvel.models.HomeImageModelHelper
import com.badjoras.marvel.models.Results
import com.badjoras.marvel.models.Thumbnail
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.holder_grid_comics.*
import kotlinx.android.extensions.LayoutContainer
import timber.log.Timber

class ComicsHolder(
    override val containerView: View,
    private val comicImageSelected: Subject<HomeImageModelHelper>
) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    private var subscriptionImage: Disposable? = null

    fun onBind(comicData: Results) {
        holderTitle.text = comicData.title

        if (hasValidThumbnail(comicData.thumbnail)) {
            holderImage.setImageURI(getThumbnailUrl(comicData.thumbnail))
        }

        clearSubscriptions()
        subscriptionImage = RxView.clicks(holderImage)
            .subscribe(
                { comicImageSelected.onNext(HomeImageModelHelper(getThumbnailUrl(comicData.thumbnail), holderImage)) },
                { error -> Timber.e(error) }
            )
    }

    private fun getThumbnailUrl(thumbnail: Thumbnail?): String {
        return thumbnail!!.path + URL_DIVIDER + thumbnail.extension
    }

    private fun hasValidThumbnail(thumbnail: Thumbnail?): Boolean {
        return (thumbnail != null && !thumbnail.extension.isNullOrEmpty()
                && !thumbnail.path.isNullOrEmpty())
    }

    private fun clearSubscriptions() {
        if (subscriptionImage != null && !subscriptionImage!!.isDisposed) {
            subscriptionImage!!.dispose()
        }
    }

    companion object {
        const val LAYOUT = R.layout.holder_grid_comics
        const val URL_DIVIDER = "."
    }
}