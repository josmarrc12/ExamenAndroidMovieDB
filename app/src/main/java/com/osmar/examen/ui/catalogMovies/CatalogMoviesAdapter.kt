package com.osmar.examen.ui.moviesList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.osmar.domain.models.ListWithMovies
import com.osmar.domain.models.Movie
import com.osmar.examen.R
import com.osmar.examen.databinding.ItemMovieBinding
import com.osmar.web.APIConstants

class CatalogMoviesAdapter: RecyclerView.Adapter<CatalogMoviesAdapter.moviesViewHoder>() {

    var onItemClick:(Movie)->Unit = {}
    var movieList: ListWithMovies? = null
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): moviesViewHoder {
        val inflater = LayoutInflater.from(parent.context)
        val vBind = ItemMovieBinding.inflate(inflater, parent, false)
        return moviesViewHoder(parent.context, vBind)
    }

    override fun onBindViewHolder(holder: moviesViewHoder, position: Int) {
        val item = movieList?.movies?.get(position)?: Movie()
        holder.onBind(item)
    }

    override fun getItemCount(): Int {
        return movieList?.movies?.size?:0
    }

    inner class moviesViewHoder(
        private val context: Context,
        private val vBind: ItemMovieBinding
    ):RecyclerView.ViewHolder(vBind.root){

        fun onBind(movie: Movie){
            vBind.tvMovieName.setText(movie.title)
            vBind.tvMoviePopularity.text = context.getString(R.string.popularity_field, (movie.popularity?:0.0).toString())
            vBind.tvMovieRate.text = context.getString(R.string.rate_field, (movie.voteAverage?:0.0).toString())
            vBind.ivMovieImage.load(APIConstants.BASE_IMG_URL+movie.backdropPath){
                this.crossfade(true)
            }
            vBind.root.setOnClickListener {
                onItemClick(movie)
            }
        }
    }
}