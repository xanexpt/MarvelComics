package com.badjoras.marvel.userinterface.homeactivity.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.badjoras.marvel.models.HomeImageModelHelper
import com.badjoras.marvel.models.Results
import io.reactivex.subjects.Subject
import timber.log.Timber

class ComicsAdapter(
    private var comicImageSelected: Subject<HomeImageModelHelper>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var comicsList: ArrayList<Results> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(ComicsHolder.LAYOUT, parent, false)
                ComicsHolder(view, comicImageSelected)
            }
            VIEW_TYPE_LOADING -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(ComicsLoadingHolder.LAYOUT, parent, false)
                ComicsLoadingHolder(view)
            }
            VIEW_TYPE_EMPTY -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(ComicsLoadingHolder.LAYOUT, parent, false)
                ComicsLoadingHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return comicsList.size+1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ComicsHolder -> {
                holder.onBind(comicsList[position])
            }
            is ComicsLoadingHolder -> {}
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            comicsList.isEmpty() -> VIEW_TYPE_EMPTY
            position<comicsList.size -> VIEW_TYPE_NORMAL
            else -> VIEW_TYPE_LOADING
        }
    }

    fun setupData(results: List<Results>) {
        comicsList = arrayListOf()
        comicsList.addAll(results)
        notifyDataSetChanged()
    }

    companion object {
        const val VIEW_TYPE_EMPTY = -1
        const val VIEW_TYPE_LOADING = 0
        const val VIEW_TYPE_NORMAL = 1
    }
}