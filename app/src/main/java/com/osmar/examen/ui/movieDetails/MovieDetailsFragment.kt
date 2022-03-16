package com.osmar.examen.ui.movieDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.osmar.domain.models.Movie
import com.osmar.examen.R
import com.osmar.examen.databinding.FragmentMovieDetailsBinding
import com.osmar.web.APIConstants
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var vBind: FragmentMovieDetailsBinding
    private val viewModel:MovieDetailsViewModel by viewModels()
    private val args: MovieDetailsFragmentArgs by navArgs()
    private var movieId:Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vBind = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        initData()
        initUpdates()
        return vBind.root
    }


    private fun initData(){
        movieId = args.movieId
    }


    private fun initUpdates(){
        viewModel.getMovie(movieId)
        viewModel.movie.observe(viewLifecycleOwner){
            showMovie(it?: Movie())
        }
    }


    private fun showMovie(movie:Movie){
        vBind.ivMovieImage.load(APIConstants.BASE_IMG_URL + movie.backdropPath){
            this.crossfade(true)
        }
        vBind.tvMovieName.text = movie.title?:""
        vBind.tvMovieOriginalName.text = movie.originalTitle?:""
        vBind.tvMovieOverview.text = movie.overview?:""
        vBind.tvMovieReleaseDate.text = getString(R.string.release_date_field,movie.releaseDate?:"")
        vBind.tvMovieOriginalLanguage.text = getString(R.string.original_language_field, movie.originalLanguage)
        vBind.tvMovieRate.text = getString(R.string.rate_field,(movie.voteAverage?:0.0).toString())
        vBind.tvMoviePopularity.text = getString(R.string.popularity_field, (movie.popularity?:0.0)?:"")
    }

}