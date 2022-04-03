package com.fmejiar.moviesapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fmejiar.moviesapp.core.BaseViewHolder
import com.fmejiar.moviesapp.data.model.Movie
import com.fmejiar.moviesapp.databinding.MovieItemBinding
import androidx.recyclerview.widget.DiffUtil.DiffResult.NO_POSITION
import com.fmejiar.moviesapp.application.AppConstants.BASE_IMAGE_URL

class UpcomingMoviesAdapter(
    private val itemClickListener: OnUpcomingMovieClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var upcomingMovies = listOf<Movie>()

    interface OnUpcomingMovieClickListener {
        fun onUpcomingMovieClick(movie: Movie, position: Int)
    }

    fun setUpcomingMovies(upcomingMovies: List<Movie>) {
        this.upcomingMovies = upcomingMovies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = UpcomingMovieViewHolder(itemBinding)

        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf { it != NO_POSITION }
                ?: return@setOnClickListener
            itemClickListener.onUpcomingMovieClick(upcomingMovies[position], position)
        }

        return holder
    }

    override fun getItemCount(): Int = upcomingMovies.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is UpcomingMovieViewHolder -> holder.bind(upcomingMovies[position])
        }
    }

    private inner class UpcomingMovieViewHolder(
        private val binding: MovieItemBinding
    ) : BaseViewHolder<Movie>(binding.root) {

        override fun bind(item: Movie) {
            Glide.with(binding.upcomingMovieImageView.context)
                .load("$BASE_IMAGE_URL${item.poster_path}")
                .centerCrop()
                .into(binding.upcomingMovieImageView)
        }

    }

}