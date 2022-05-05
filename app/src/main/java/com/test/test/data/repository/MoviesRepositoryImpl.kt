package com.test.test.data.repository

import com.test.test.R
import com.test.test.data.json.JsonConverter
import com.test.test.data.local.MoviesDatabase
import com.test.test.data.mapper.toMovieListing
import com.test.test.data.mapper.toMovieListingEntity
import com.test.test.data.remote.MoviesApi
import com.test.test.data.util.ReleaseDateConverter
import com.test.test.domain.model.MovieListing
import com.test.test.domain.repository.MoviesRepository
import com.test.test.util.Resource
import com.test.test.util.StringResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    private val moviesApi: MoviesApi,
    db: MoviesDatabase,
    private val jsonConverter: JsonConverter,
    private val releaseDateConverter: ReleaseDateConverter
) : MoviesRepository {

    private val moviesDao = db.moviesDao

    override suspend fun getMoviesListings(
        query: String
    ): Flow<Resource<List<MovieListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localMovieListings = moviesDao.searchMovieListing(query)
            emit(Resource.Success(data = localMovieListings.map { it.toMovieListing(releaseDateConverter) }))
            // Проверяем что локальная база пуста.
            // localMovieListings будет пустым если попытаться найти фильм, но ничего не обнаружить, поэтому необходимо проверить оба условия.
            if (localMovieListings.isEmpty() && query.isBlank()) {
                val remoteMovieListings = try {
                        jsonConverter.convertMoviesListings(moviesApi.getMovies().string())

                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(Resource.Error(StringResource(R.string.cant_load_data)))
                    null
                } catch (e: HttpException) {
                    e.printStackTrace()
                    emit(Resource.Error(StringResource(R.string.cant_load_data)))
                    null
                }
                remoteMovieListings?.let {
                    moviesDao.insertMovieListings(remoteMovieListings.map { it.toMovieListingEntity() })
                    emit(
                        // Вернуть загруженные в кэш данные
                        Resource.Success(
                            moviesDao.searchMovieListing("").map { it.toMovieListing(releaseDateConverter) })
                    )
                }
            }
            emit(Resource.Loading(false))
        }
    }
}