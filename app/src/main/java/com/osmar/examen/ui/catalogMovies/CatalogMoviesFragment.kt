package com.osmar.examen.ui.moviesList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.osmar.domain.models.MovieList
import com.osmar.examen.R
import com.osmar.examen.databinding.FragmentCatalogMoviesBinding
import com.osmar.web.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "MoviesListFragment"

@AndroidEntryPoint
class MoviesListFragment : Fragment() {

    private lateinit var vBind: FragmentCatalogMoviesBinding
    private val viewModelCatalog:CatalogMoviesViewModel by viewModels()
    private val moviesAdapter = CatalogMoviesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vBind = FragmentCatalogMoviesBinding.inflate(inflater, container,false)
        initRv()
        initUpdates()
        initSpinner()
        return vBind.root
    }


    private fun initUpdates(){
        viewModelCatalog.getMovies()
        viewModelCatalog.updateMoviesByApi()
        viewModelCatalog.movies.observe(viewLifecycleOwner){ listWithMovies ->
            moviesAdapter.movieList = listWithMovies
        }
        viewModelCatalog.onUpdateMovies.observe(viewLifecycleOwner){ onUpdateMovies ->
            val isLoading = onUpdateMovies is NetworkResult.Loading
            vBind.pbLoading.visibility = if(isLoading) View.VISIBLE else View.GONE
            vBind.spListType.isEnabled = !isLoading
            if(onUpdateMovies is NetworkResult.Error){
                Toast.makeText(context, getString(R.string.err), Toast.LENGTH_SHORT).show()
                Log.e(TAG, "onUpdateMovies: "+onUpdateMovies.message)
            }
            if(onUpdateMovies is NetworkResult.Success){
                viewModelCatalog.getMovies()
            }
            setupPagination(onUpdateMovies.data?:viewModelCatalog.movies.value?.list?: MovieList(), isLoading)
        }
    }


    private fun initRv(){
        vBind.rvMovies.adapter = moviesAdapter
        moviesAdapter.onItemClick = { movie ->
            val action = MoviesListFragmentDirections.moviesListToMovieDetails(movie.movieId)
            findNavController().navigate(action)
        }
    }


    private fun initSpinner(){
        vBind.spListType.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapter: AdapterView<*>?, view: View?, selection: Int, viewId: Long) {
                if(selection+1 != viewModelCatalog.type){
                    viewModelCatalog.type = selection+1
                    viewModelCatalog.page = 1
                    viewModelCatalog.getMovies()
                    viewModelCatalog.updateMoviesByApi()
                }
            }

            override fun onNothingSelected(adapter: AdapterView<*>?) {}

        }
    }


    private fun setupPagination(movieList:MovieList, isLoading:Boolean){
        val isLastPage = viewModelCatalog.page>=movieList.totalPages?:0
        val isFirstPage = viewModelCatalog.page == 1
        vBind.bPagPrevius.setOnClickListener {
            viewModelCatalog.page--
            viewModelCatalog.getMovies()
            viewModelCatalog.updateMoviesByApi()
        }
        vBind.bPagNext.setOnClickListener {
            viewModelCatalog.page++
            viewModelCatalog.getMovies()
            viewModelCatalog.updateMoviesByApi()
        }
        vBind.bPagPrevius.visibility = if(!isFirstPage && !isLoading) View.VISIBLE else View.GONE
        vBind.bPagNext.visibility = if(!isLastPage && !isLoading) View.VISIBLE else View.GONE

    }
}