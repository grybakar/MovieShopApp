package com.grybakar.onlinemovieshopapp.service;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TmdbApiService {

  public static final String LANGUAGE = "en-US";
  private final TmdbApi api;

  public TmdbApiService() {
    this.api = new TmdbApi("eef3ab7073176d58c53449962c89fce2");
  }

  public MovieDb getMovieDetails(Integer movieId) {
    return api.getMovies().getMovie(movieId, LANGUAGE);
  }

  public List<MovieDb> searchMovies(String query) {
    return api.getSearch().searchMovie(query, null, LANGUAGE, false, 1).getResults();
  }
}
