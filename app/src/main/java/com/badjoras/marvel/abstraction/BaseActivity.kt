package com.badjoras.marvel.abstraction

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity : AppCompatActivity() {

    private var presenter: UiContract.Presenter? = null
    private lateinit var createSubscriptions: CompositeDisposable
    protected abstract fun getPresenter(): UiContract.Presenter?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = CompositePresenter(getPresenter())
        presenter?.setInitialState(savedInstanceState ?: intent.extras)
        createSubscriptions = CompositeDisposable()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        presenter?.onNewIntent(intent)
    }

    override fun onStart() {
        super.onStart()
        presenter?.onStart()
    }

    override fun onResume() {
        super.onResume()
        presenter?.onResume()
    }

    override fun onPause() {
        presenter?.onPause()
        cancelSubscribers()
        super.onPause()
    }

    override fun onStop() {
        presenter?.onStop()
        super.onStop()
    }

    private fun addSubscriptions(subscription: Disposable) {
        createSubscriptions.add(subscription)
    }

    private fun cancelSubscribers() {
        if (!createSubscriptions.isDisposed) {
            createSubscriptions.dispose()
        }
    }

    public override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter?.let {
            val state = presenter!!.getCurrentState()
            outState.putAll(state)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        presenter?.setInitialState(savedInstanceState)
    }
}