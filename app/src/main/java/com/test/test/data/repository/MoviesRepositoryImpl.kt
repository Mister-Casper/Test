package com.test.test.data.repository

import com.test.test.R
import com.test.test.data.json.JsonConverter
import com.test.test.data.local.CharacterListingEntry
import com.test.test.data.local.MoviesDatabase
import com.test.test.data.mapper.toCharacterLingEntity
import com.test.test.data.mapper.toCharacterListing
import com.test.test.data.mapper.toMovieListing
import com.test.test.data.mapper.toMovieListingEntity
import com.test.test.data.remote.MoviesApi
import com.test.test.data.util.ReleaseDateConverter
import com.test.test.domain.model.CharacterListing
import com.test.test.domain.model.MovieListing
import com.test.test.domain.repository.MoviesRepository
import com.test.test.util.Resource
import com.test.test.util.StringResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
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
    private val characterDao = db.charactersDao

    override suspend fun getMoviesListings(
        query: String
    ): Flow<Resource<List<MovieListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localMovieListings = moviesDao.searchMovieListing(query)
            emit(Resource.Success(data = localMovieListings.map {
                it.toMovieListing(
                    releaseDateConverter
                )
            }))
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
                            moviesDao.searchMovieListing("")
                                .map { it.toMovieListing(releaseDateConverter) })
                    )
                }
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getCharacters(episodeId: Int): Flow<Resource<List<CharacterListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val remoteCharacters = mutableListOf<CharacterListingEntry>()
            val characterIds =
                moviesDao.searchMovieListing("").find { it.episode == episodeId }!!.charactersId
            characterIds.forEach { id ->
                val character = characterDao.getCharacterById(id)
                if (character == null) {
                    try {
                        remoteCharacters.add(
                            jsonConverter.convertToCharacterListing(
                                moviesApi.getCharacter(
                                    id
                                ).string()
                            ).toCharacterLingEntity()
                        )
                    } catch (e: IOException) {
                        e.printStackTrace()
                        emit(Resource.Error(StringResource(R.string.cant_load_data)))
                        return@flow
                    } catch (e: HttpException) {
                        e.printStackTrace()
                        emit(Resource.Error(StringResource(R.string.cant_load_data)))
                        return@flow
                    }
                }
            }
            characterDao.insertCharacterListings(remoteCharacters)
            emit(Resource.Loading(false))
            emit(
                Resource.Success(
                    characterDao.getLocalCharacters().map { it.toCharacterListing() })
            )
        }
    }

    override suspend fun getEpisodeName(episodeId: Int): String {
        return moviesDao.getEpisodeName(episodeId)
    }

}