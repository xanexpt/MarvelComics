package com.badjoras.marvel.abstraction

import android.content.Intent
import android.os.Bundle
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter : UiContract.Presenter {

    private var onResumeSubscriptions: CompositeDisposable? = null
    private var onStartSubscriptions: CompositeDisposable? = null

    override fun getCurrentState(): Bundle {
        return Bundle.EMPTY
    }

    override fun setInitialState(state: Bundle?) {

    }

    override fun onNewIntent(intent: Intent?) {

    }

    override fun onResume() {
        onResumeSubscriptions = CompositeDisposable()
    }

    protected fun addOnResumeSubscription(subscription: Disposable) {
        if (onResumeSubscriptions != null) {
            onResumeSubscriptions!!.add(subscription)
        }
    }

    override fun onPause() {
        if (onResumeSubscriptions != null && !onResumeSubscriptions!!.isDisposed) {
            onResumeSubscriptions!!.dispose()
            onResumeSubscriptions = null
        }
    }

    override fun onStart() {
        onStartSubscriptions = CompositeDisposable()
    }

    protected fun addOnStartSubscription(subscription: Disposable) {
        onStartSubscriptions!!.add(subscription)
    }

    override fun onStop() {
        if (onStartSubscriptions != null && !onStartSubscriptions!!.isDisposed) {
            onStartSubscriptions!!.dispose()
            onStartSubscriptions = null
        }
    }

}