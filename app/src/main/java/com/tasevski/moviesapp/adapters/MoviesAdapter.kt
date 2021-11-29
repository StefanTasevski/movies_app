package com.tasevski.moviesapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tasevski.moviesapp.DetailsActivity
import com.tasevski.moviesapp.databinding.ListItemBinding
import com.tasevski.moviesapp.model.Movie

class MoviesAdapter : ListAdapter<Movie, MoviesViewHolder>(MoviesComparator()) {
    private var parentObject: ViewGroup? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        parentObject = parent
        val binding =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
        holder.itemView.setOnClickListener {
            if(parentObject!=null) {
                val intent = Intent(parentObject?.context, DetailsActivity::class.java)
                intent.putExtra("title", currentItem.title)
                intent.putExtra("plotSynopsis", currentItem.plotSynopsis)
                intent.putExtra("posterPath", currentItem.posterPath)
                startActivity(parentObject!!.context, intent, null)
            }
        }
    }
}

class MoviesViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val imageUrl = "http://image.tmdb.org/t/p/w500"

    fun bind(movie: Movie) {
        binding.apply {
            Glide.with(itemView)
                .load(imageUrl+movie.posterPath)
                .into(imageViewLogo)
            title.text = movie.title
            overview.text = movie.plotSynopsis
        }
    }
}

class MoviesComparator : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
        oldItem == newItem
}