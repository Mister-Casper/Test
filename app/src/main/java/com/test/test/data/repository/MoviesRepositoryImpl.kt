package com.test.test.data.repository

import android.content.Context
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
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
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
    private val releaseDateConverter: ReleaseDateConverter,
    private val context:Context
) : MoviesRepository {

    private val moviesDao = db.moviesDao
    private val characterDao = db.charactersDao

    override suspend fun getMoviesListings(
        query: String
    ): Flow<Resource<List<MovieListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localMovieListings = moviesDao.searchMovieListing(query)

            if (localMovieListings.isEmpty() && query.isBlank()) {
                val remoteMovieListings = try {
                    jsonConverter.convertMoviesListings(moviesApi.getMovies().string())

                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(Resource.Error(context.getString(R.string.cant_load_data)))
                    null
                } catch (e: HttpException) {
                    e.printStackTrace()
                    emit(Resource.Error(context.getString(R.string.cant_load_data)))
                    null
                }
                remoteMovieListings?.let {
                    moviesDao.insertMovieListings(remoteMovieListings.map { it.toMovieListingEntity() })
                    emit(
                        Resource.Success(
                            moviesDao.searchMovieListing("")
                                .map { it.toMovieListing(releaseDateConverter) })
                    )
                }
            }else if(localMovieListings.isNotEmpty()){
                emit(Resource.Success(data = localMovieListings.map {
                    it.toMovieListing(
                        releaseDateConverter
                    )
                }))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getCharacters(episodeId: Int): Flow<Resource<List<CharacterListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val characters = mutableListOf<Deferred<CharacterListingEntry>>()
            val characterIds =
                moviesDao.searchMovieListing("").find { it.episode == episodeId }!!.charactersId
            coroutineScope {
                characterIds.forEach { id ->
                    val character = characterDao.getCharacterById(id)
                    if (character == null) {
                        try {
                            characters.add(
                                async {
                                    jsonConverter.convertToCharacterListing(
                                        moviesApi.getCharacter(
                                            id
                                        ).string()
                                    ).toCharacterLingEntity()
                                }
                            )
                        } catch (e: IOException) {
                            e.printStackTrace()
                            emit(Resource.Loading(false))
                            emit(Resource.Error(context.getString(R.string.cant_load_data)))
                            return@coroutineScope
                        } catch (e: HttpException) {
                            e.printStackTrace()
                            emit(Resource.Loading(false))
                            emit(Resource.Error(context.getString(R.string.cant_load_data)))
                            return@coroutineScope
                        }
                    } else
                        characters.add(async{characterDao.getCharacterById(id)!!})
                }
            }
            characterDao.insertCharacterListings(characters.awaitAll())
            emit(Resource.Loading(false))
            emit(Resource.Success(characters.awaitAll().map { it.toCharacterListing() }))
        }
    }

    override suspend fun getEpisodeName(episodeId: Int): String {
        return moviesDao.getEpisodeName(episodeId)
    }

}