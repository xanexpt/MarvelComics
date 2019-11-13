package com.badjoras.marvel.abstraction

import android.content.Intent
import android.os.Bundle

class CompositePresenter constructor(
    private val presenter: UiContract.Presenter?
) : UiContract.Presenter {

    override fun getCurrentState(): Bundle {
        val finalState = Bundle()
        val presenterState = presenter?.getCurrentState()
        if (presenterState!=null && presenterState.keySet().any { key ->
                finalState.containsKey(key)
            }) {
            throw RuntimeException(
                "--> ${CompositePresenter::class.java.name} found duplicate keys.")
        }

        if(presenterState!=null){
            finalState.putAll(presenterState)
        }

        return finalState
    }

    override fun onNewIntent(intent: Intent?) {
        presenter?.onNewIntent(intent)
    }

    override fun onStart() {
        presenter?.onStart()
    }

    override fun onStop() {
        presenter?.onStop()
    }

    override fun onResume() {
        presenter?.onResume()
    }

    override fun onPause() {
        presenter?.onPause()
    }

    override fun setInitialState(state: Bundle?) {
        presenter?.setInitialState(state)
    }
}