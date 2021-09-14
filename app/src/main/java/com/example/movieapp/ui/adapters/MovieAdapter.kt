package com.example.movieapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.movieapp.databinding.ItemMovieBinding
import com.example.movieapp.data.models.Movie
import com.example.movieapp.utils.Constants.Companion.BASE_URL_IMAGE
import javax.inject.Inject

class MovieAdapter @Inject constructor( private val glide: RequestManager) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.binding.apply {
            tvMovieTitle.text = minimizeTitle(movie.title)
            tvPopularity.text = "Popularity: "+movie.popularity

            glide.load(BASE_URL_IMAGE+movie.poster_path).into(ivPoster)
            ivPoster.background = null
            root.setOnClickListener{
                onItemClickListener?.let { click ->
                    click(movie)
                }
            }
        }
    }

    protected var onItemClickListener: ((Movie) -> Unit)? = null
    fun setItemClickListener(listener: (Movie) -> Unit) {
        onItemClickListener = listener
    }

    private fun minimizeTitle(title: String):String {
        if (title.length > 10) {
            return title.substring(0,10) + "..."
        }
        return title
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}