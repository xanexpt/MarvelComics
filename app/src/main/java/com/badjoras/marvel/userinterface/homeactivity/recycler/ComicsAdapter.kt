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
        val view = LayoutInflater.from(parent.context)
            .inflate(ComicsHolder.LAYOUT, parent, false)
        return ComicsHolder(view, comicImageSelected)
    }

    override fun getItemCount(): Int {
        return comicsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemVideoHolder = holder as ComicsHolder
        itemVideoHolder.onBind(comicsList[position])
    }

    fun setupData(results: List<Results>) {
        comicsList = arrayListOf()
        comicsList.addAll(results)
        Timber.e("TAMANHO ${comicsList.size}")
        notifyDataSetChanged()
    }

}