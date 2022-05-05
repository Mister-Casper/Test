package com.test.test.presentation.movies

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.test.R
import com.test.test.domain.model.MovieListing

@Composable
fun MovieItem(movie: MovieListing, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Text(text = stringResource(id = R.string.movie_name,movie.movieName), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text =  stringResource(id = R.string.director_name,movie.directorName), fontSize = 16.sp)
            Text(text = stringResource(id = R.string.producer_name,movie.producerName), fontSize = 16.sp)
            Text(text = stringResource(id = R.string.year,movie.year), fontSize = 16.sp, color = Color.Red,fontStyle = FontStyle.Italic,)
        }
    }
}