package com.badjoras.marvel.userinterface.homeactivity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.badjoras.marvel.BaseApplication
import com.badjoras.marvel.R
import com.badjoras.marvel.abstraction.BaseActivity
import com.badjoras.marvel.abstraction.UiContract
import com.badjoras.marvel.models.HomeImageModelHelper
import com.badjoras.marvel.models.Results
import com.badjoras.marvel.services.MarvelServices
import com.badjoras.marvel.userinterface.homeactivity.recycler.ComicsAdapter
import com.badjoras.marvel.userinterface.homeactivity.recycler.PaginationInfoModel
import com.badjoras.marvel.userinterface.homeactivity.recycler.PaginationScrollListener
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.activity_home.*
import timber.log.Timber

class HomeActivity : BaseActivity(), HomeContract.View {

    private var gridLayoutManager: GridLayoutManager? = null
    private var comicsAdapter: ComicsAdapter? = null

    private var currentAnimator: Animator? = null
    private var shortAnimationDurationMilliseconds: Int = 500

    private lateinit var comicImageSelected: Subject<HomeImageModelHelper>
    private lateinit var loadMoreItemsSubject: Subject<PaginationInfoModel>

    override fun getPresenter(): UiContract.Presenter? {
        val mainInjector = (application as BaseApplication).applicationComponent!!

        val service = MarvelServices()
        mainInjector.inject(service)

        val presenter = HomePresenter(this, service)
        mainInjector.inject(presenter)

        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(LAYOUT)
        comicImageSelected = PublishSubject.create()
        loadMoreItemsSubject = PublishSubject.create()
        prepareRecyclerView()
    }

    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    private fun prepareRecyclerPaginationListener() {
        homeGridRecycler?.addOnScrollListener(object : PaginationScrollListener(gridLayoutManager!!) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                loadMoreItemsSubject.onNext(PaginationInfoModel(comicsAdapter!!.getComicListSize()))
            }
        })
    }

    private fun prepareRecyclerView() {
        gridLayoutManager = GridLayoutManager(this, 2)
        homeGridRecycler.layoutManager = gridLayoutManager

        comicsAdapter = ComicsAdapter(comicImageSelected)
        homeGridRecycler.adapter = comicsAdapter
    }

    override fun prepareRecyclerData(results: List<Results>) {
        if (comicsAdapter != null) {
            isLoading = false
            comicsAdapter?.setupData(results)
            prepareRecyclerPaginationListener()
        }
    }

    override fun addMorePagesToRecycler(results: List<Results>) {
        if (comicsAdapter != null) {
            isLoading = false
            comicsAdapter?.addPages(results)
        }
    }

    override fun setupLoadMoreItemsEvent(): Observable<PaginationInfoModel> = loadMoreItemsSubject

    override fun setupImageSelected(): Observable<HomeImageModelHelper> = comicImageSelected
        .doOnNext { modelSelected -> zoomImageFromThumb(modelSelected.imgView, modelSelected.url) }

    /**
     * https://developer.android.com/training/animation/zoom
     */
    private fun zoomImageFromThumb(thumbView: View, imageUrl: String) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        currentAnimator?.cancel()

        // Load the high-resolution "zoomed-in" image.
        expandedImageView.setImageURI(imageUrl)

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        val startBoundsInt = Rect()
        val finalBoundsInt = Rect()
        val globalOffset = Point()

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBoundsInt)
        container.getGlobalVisibleRect(finalBoundsInt, globalOffset)
        startBoundsInt.offset(-globalOffset.x, -globalOffset.y)
        finalBoundsInt.offset(-globalOffset.x, -globalOffset.y)

        val startBounds = RectF(startBoundsInt)
        val finalBounds = RectF(finalBoundsInt)

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        val startScale: Float
        if ((finalBounds.width() / finalBounds.height() > startBounds.width() / startBounds.height())) {
            // Extend start bounds horizontally
            startScale = startBounds.height() / finalBounds.height()
            val startWidth: Float = startScale * finalBounds.width()
            val deltaWidth: Float = (startWidth - startBounds.width()) / 2
            startBounds.left -= deltaWidth.toInt()
            startBounds.right += deltaWidth.toInt()
        } else {
            // Extend start bounds vertically
            startScale = startBounds.width() / finalBounds.width()
            val startHeight: Float = startScale * finalBounds.height()
            val deltaHeight: Float = (startHeight - startBounds.height()) / 2f
            startBounds.top -= deltaHeight.toInt()
            startBounds.bottom += deltaHeight.toInt()
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.alpha = 0f
        expandedImageView.visibility = View.VISIBLE

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.pivotX = 0f
        expandedImageView.pivotY = 0f

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        currentAnimator = AnimatorSet().apply {
            play(ObjectAnimator.ofFloat(
                expandedImageView,
                View.X,
                startBounds.left,
                finalBounds.left)
            ).apply {
                with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top, finalBounds.top))
                with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale, 1f))
                with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale, 1f))
            }
            duration = shortAnimationDurationMilliseconds.toLong()
            interpolator = DecelerateInterpolator()
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationEnd(animation: Animator) {
                    currentAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    currentAnimator = null
                }
            })
            start()
        }

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        expandedImageView.setOnClickListener {
            currentAnimator?.cancel()

            // Animate the four positioning/sizing properties in parallel,
            // back to their original values.
            currentAnimator = AnimatorSet().apply {
                play(ObjectAnimator.ofFloat(expandedImageView, View.X, startBounds.left)).apply {
                    with(ObjectAnimator.ofFloat(expandedImageView, View.Y, startBounds.top))
                    with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X, startScale))
                    with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_Y, startScale))
                }
                duration = shortAnimationDurationMilliseconds.toLong()
                interpolator = DecelerateInterpolator()
                addListener(object : AnimatorListenerAdapter() {

                    override fun onAnimationEnd(animation: Animator) {
                        thumbView.alpha = 1f
                        expandedImageView.visibility = View.GONE
                        currentAnimator = null
                    }

                    override fun onAnimationCancel(animation: Animator) {
                        thumbView.alpha = 1f
                        expandedImageView.visibility = View.GONE
                        currentAnimator = null
                    }
                })
                start()
            }
        }
    }

    companion object {
        fun getNavigationIntent(parentActivity: AppCompatActivity): Intent {
            return Intent(parentActivity, HomeActivity::class.java)
        }

        const val LAYOUT = R.layout.activity_home
    }
}