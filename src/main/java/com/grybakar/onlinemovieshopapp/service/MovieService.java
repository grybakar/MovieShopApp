package com.grybakar.onlinemovieshopapp.service;

import com.grybakar.onlinemovieshopapp.dto.MovieDto;
import com.grybakar.onlinemovieshopapp.exception.MovieApplicationNotFoundException;
import com.grybakar.onlinemovieshopapp.mapper.MovieMapper;
import com.grybakar.onlinemovieshopapp.model.Actor;
import com.grybakar.onlinemovieshopapp.model.Director;
import com.grybakar.onlinemovieshopapp.model.Movie;
import com.grybakar.onlinemovieshopapp.populator.MoviePopulator;
import com.grybakar.onlinemovieshopapp.repository.MovieRepository;
import info.movito.themoviedbapi.model.MovieDb;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@AllArgsConstructor
@Slf4j
public class MovieService {

  private static final String PATH_MOVIE = "http://localhost:8080/api/movies/{id}";

  private final MovieRepository movieRepository;
  private final MoviePopulator moviePopulator;
  private final MovieMapper movieMapper;
  private final TmdbApiService apiService;
  private final ActorService actorService;

  private final DirectorService directorService;

  /**
   * Searches the movie database from the "tmdb" API using the given query and returns a list of movie DTOs.
   *
   * @param query the search query to use
   * @return a list of movie DTOs representing the search results
   */
  public List<MovieDto> searchMoviesInApi(String query) {
    log.info("Finding movies In API by given query: {}", query);
    List<MovieDb> searchResult = apiService.searchMovies(query);
    return searchResult
      .stream()
      .map(movieMapper::toMovieDto)
      .toList();
  }

  /**
   * Finds all movies in the database.
   *
   * @return a list of movie DTOs
   */
  public List<MovieDto> searchMoviesInDatabase() {
    log.info("Finding movies");

    List<Movie> movies = movieRepository.findAll();
    return movies
      .stream()
      .map(movieMapper::toMovieDto)
      .toList();
  }

  public MovieDto searchMovieById(Long id) {
    log.info("Finding movie by id:{}", id);
    return movieRepository
      .findById(id)
      .map(movieMapper::toMovieDto)
      .orElseThrow(() -> new MovieApplicationNotFoundException("No movie found by given id: %d".formatted(id)));
  }

  public List<MovieDto> searchMoviesByDirectorFullName(String fullName) {
    log.info("Finding movies by Director:{}", fullName);
    return movieRepository
      .findByDirectorsFullNameIgnoreCase(fullName)
      .stream()
      .map(movieMapper::toMovieDto)
      .toList();
  }

  public List<MovieDto> searchMovieByTitleOrActorOrDirector(String query) {
    return movieRepository
      .searchByTitleOrActorOrDirector(query)
      .stream()
      .map(movieMapper::toMovieDto)
      .toList();
  }

  /**
   * Saves a movie to the database and returns the saved movie DTO. (Additionally populates the dto with missing data
   * from another API call)
   *
   * @param movieDto the data we get from front-end save to database
   * @return a saved movie DTO
   */

  @Transactional
  public MovieDto saveMovie(MovieDto movieDto) {

    Optional<Movie> existingMovie = checkIfMovieExistsInDb(movieDto.getTmdbId());
    boolean movieExists = existingMovie.isPresent();
    if (movieExists) {
      log.info("Movie with tmdbId {} already exists in the database, returning existing movie", movieDto.getTmdbId());
      return movieMapper.toMovieDto(existingMovie.get());
    }

    moviePopulator.populateMovieData(movieDto);
    Movie movie = movieMapper.toMovieEntity(movieDto);

    Set<Actor> cast = actorService.getCastAndSaveToDb(movieDto.getTmdbId());
    movie.setCast(cast);

    Set<Director> directors = directorService.getDirectorsAndSaveToDb(movie.getTmdbId());
    movie.setDirectors(directors);

    Movie savedMovie = movieRepository.save(movie);

    log.info("Saving to database movie: {}", movie.getTitle());
    return movieMapper.toMovieDto(savedMovie);
  }

  private Optional<Movie> checkIfMovieExistsInDb(Integer tmdbId) {
    return movieRepository.findByTmdbId(tmdbId);
  }

  public URI createMovieURI(Long id) {
    return UriComponentsBuilder
      .fromPath(PATH_MOVIE)
      .buildAndExpand(id)
      .toUri();
  }

  public void deleteMovie(Long id) {
    log.warn("Deleting movies by id: {}", id);
    movieRepository.deleteById(id);
  }
}

