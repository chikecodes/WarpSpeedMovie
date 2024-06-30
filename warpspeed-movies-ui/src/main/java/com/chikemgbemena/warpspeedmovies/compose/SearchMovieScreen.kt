package com.chikemgbemena.warpspeedmovies.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import com.chikemgbemena.warpspeed.domain.models.Movie
import com.chikemgbemena.warpspeed.presentation.viewmodel.SearchMovieUIModel
import com.chikemgbemena.warpspeed.presentation.viewmodel.SearchMovieViewModel
import com.chikemgbemena.warpspeedmovies.R
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMovieScreen(
    viewModel: SearchMovieViewModel = hiltViewModel(),
    onClickListener: (movie: Movie) -> Unit
) {
    val movieListLiveData: LiveData<SearchMovieUIModel> = viewModel.getMoviesLiveData()
    val movieUIModel by movieListLiveData.observeAsState(initial = null)

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.search_movies)) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text(stringResource(id = R.string.enter_movie_title)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    viewModel.searchMovie(searchQuery.text)
                    focusManager.clearFocus()
                    keyboardController?.hide() },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(id = R.string.search))
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (movieUIModel) {
                is SearchMovieUIModel.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                is SearchMovieUIModel.Error -> {
                    val errorResId = (movieUIModel as SearchMovieUIModel.Error).error
                    Text(
                        text = stringResource(id = errorResId),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                is SearchMovieUIModel.Success -> {
                    val movies = (movieUIModel as SearchMovieUIModel.Success).data
                    LazyColumn {
                        items(movies) { movie ->
                            MovieItem(movie = movie, onClickListener)
                        }
                    }
                }
                else -> Timber.d("empty value")
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie, onClickListener: (movie: Movie) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(
                onClick = {
                    onClickListener(movie)
                }
            )
    ) {
        Text(text = movie.title, style = MaterialTheme.typography.titleLarge)
        Text(text = movie.year, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(4.dp))
    }
}