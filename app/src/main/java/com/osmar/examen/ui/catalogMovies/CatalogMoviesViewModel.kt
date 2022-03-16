package com.osmar.examen.ui.moviesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osmar.domain.models.*
import com.osmar.examen.repository.MoviesRepository
import com.osmar.web.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CatalogMoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
):ViewModel() {
    private val _movies = MutableLiveData<ListWithMovies?>()
    private val _onUpdateMovies = MutableLiveData<NetworkResult<MovieList>>()

    val movies:LiveData<ListWithMovies?> = _movies
    val onUpdateMovies:LiveData<NetworkResult<MovieList>> = _onUpdateMovies
    var page:Int = 1
    var type:Int = 1


    fun getMovies() = viewModelScope.launch(Dispatchers.IO){
        moviesRepository.getList(type, page).collect{
            _movies.postValue(it)
        }
    }


    fun updateMoviesByApi() = viewModelScope.launch(Dispatchers.IO){
        moviesRepository.updateListbyApi(type, page).collect{
            _onUpdateMovies.postValue(it)
        }
    }
}