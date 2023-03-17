package com.grybakar.onlinemovieshopapp.service;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.people.PersonCrew;
import info.movito.themoviedbapi.model.people.PersonPeople;
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

  public List<PersonCast> getCast(Integer movieId) {
    return api.getMovies().getCredits(movieId).getCast();
  }

  public PersonPeople getPersonDetails(Integer tmdbId) {
    return api.getPeople().getPersonInfo(tmdbId);
  }

  public List<PersonCrew> getDirectors(Integer movieId) {
    return api
      .getMovies()
      .getCredits(movieId)
      .getCrew()
      .stream()
      .filter(crew -> crew.getJob().equalsIgnoreCase("Director"))
      .toList();
  }

  public List<MovieDb> searchMovies(String query) {
    return api.getSearch().searchMovie(query, null, LANGUAGE, false, 1).getResults();
  }
}
