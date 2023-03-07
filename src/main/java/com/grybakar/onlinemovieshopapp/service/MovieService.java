package com.grybakar.onlinemovieshopapp.service;

import com.grybakar.onlinemovieshopapp.dto.MovieDto;
import com.grybakar.onlinemovieshopapp.exception.MovieApplicationNotFoundException;
import com.grybakar.onlinemovieshopapp.mapper.MovieMapper;
import com.grybakar.onlinemovieshopapp.model.Movie;
import com.grybakar.onlinemovieshopapp.repository.MovieRepository;
import info.movito.themoviedbapi.model.MovieDb;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@AllArgsConstructor
@Slf4j
public class MovieService {

  private static final String PATH_MOVIE = "http://localhost:8080/api/movies/{id}";

  private final MovieRepository movieRepository;
  private final TmdbApiService apiService;
  private final MovieMapper movieMapper;

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

  /**
   * Saves a movie to the database and returns the saved movie DTO. (Additionally populates the dto with missing data
   * from another API call)
   *
   * @param movieDto the data we get from front-end save to database
   * @return a saved movie DTO
   */

  public MovieDto saveMovie(MovieDto movieDto) {
    addAdditionalMovieDetails(movieDto);
    Movie movie = movieMapper.toMovieEntity(movieDto);
    Movie savedMovie = movieRepository.save(movie);
    log.info("Saving to database movie: {}", movie.getTitle());
    return movieMapper.toMovieDto(savedMovie);
  }

  private void addAdditionalMovieDetails(MovieDto movieDto) {
    MovieDb movieDetails = apiService.getMovieDetails(movieDto.getTmdbId());
    movieDto.setDuration(movieDetails.getRuntime());
    log.info("Setting additional movie data: runtime: {}, price: {}", movieDto.getDuration(), movieDto.getPrice());
    //TODO ATM PRICE IS DEFAULT 9.99.
    movieDto.setPrice(BigDecimal.valueOf(9.99));
  }

  public URI createMovieURI(Long id) {
    return UriComponentsBuilder
      .fromPath(PATH_MOVIE)
      .buildAndExpand(id)
      .toUri();
  }

  public MovieDto searchMovieById(Long id) {
    log.info("Finding movie by id:{}", id);
    return movieRepository
      .findById(id)
      .map(movieMapper::toMovieDto)
      .orElseThrow(() -> new MovieApplicationNotFoundException("No movie found by given id: %d".formatted(id)));
  }

  public void deletePosition(Long id) {
    log.warn("Deleting movies by id: {}", id);
    movieRepository.deleteById(id);
  }
}

