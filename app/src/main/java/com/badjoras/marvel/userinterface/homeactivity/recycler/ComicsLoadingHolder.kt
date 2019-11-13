package com.badjoras.marvel.userinterface.homeactivity.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.badjoras.marvel.R
import kotlinx.android.extensions.LayoutContainer

class ComicsLoadingHolder(
    override val containerView: View
) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    companion object {
        const val LAYOUT = R.layout.holder_loading
    }
}