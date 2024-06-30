package com.chikemgbemena.warpspeedmovies.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import coil.compose.AsyncImage
import com.chikemgbemena.warpspeed.domain.models.Movie
import com.chikemgbemena.warpspeed.presentation.viewmodel.GetMovieDetailUIModel
import com.chikemgbemena.warpspeed.presentation.viewmodel.GetMovieViewModel
import com.chikemgbemena.warpspeedmovies.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    imdbId: String,
    viewModel: GetMovieViewModel = hiltViewModel(),
    popBack: () -> Unit
) {

    LaunchedEffect(imdbId) {
        viewModel.getMovieDetail(imdbId)
    }

    val movieDetailLiveData: LiveData<GetMovieDetailUIModel> = viewModel.getMovieDetailLiveData()
    val movieDetailUIModel by movieDetailLiveData.observeAsState(initial = GetMovieDetailUIModel.Loading)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.movie_details)) },
                navigationIcon = {
                    IconButton(onClick = { popBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
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
            when (movieDetailUIModel) {
                is GetMovieDetailUIModel.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                is GetMovieDetailUIModel.Error -> {
                    val errorResId = (movieDetailUIModel as GetMovieDetailUIModel.Error).error
                    Text(
                        text = stringResource(id = errorResId),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                is GetMovieDetailUIModel.Success -> {
                    val movie = (movieDetailUIModel as GetMovieDetailUIModel.Success).data
                    MovieDetailContent(movie = movie)
                }
            }
        }
    }
}

@Composable
fun MovieDetailContent(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = movie.title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Year: ${movie.year}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Type: ${movie.type}", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            model = movie.poster,
            contentDescription = null,
            modifier = Modifier.size(400.dp),
            contentScale = ContentScale.Crop
        )
    }
}