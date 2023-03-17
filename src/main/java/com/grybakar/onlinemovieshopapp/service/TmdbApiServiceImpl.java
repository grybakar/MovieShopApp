package com.grybakar.onlinemovieshopapp.service;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.people.PersonCrew;
import info.movito.themoviedbapi.model.people.PersonPeople;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TmdbApiServiceImpl implements TmdbApiService {

  private final TmdbApi api;
  public static final String LANGUAGE = "en-US";


  public TmdbApiServiceImpl() {
    this.api = new TmdbApi("eef3ab7073176d58c53449962c89fce2");
  }

  @Override
  public MovieDb getMovieDetails(Integer movieApiId) {
    return api.getMovies().getMovie(movieApiId, LANGUAGE);
  }

  @Override
  public List<PersonCast> getCast(Integer movieApiId) {
    return api.getMovies().getCredits(movieApiId).getCast();
  }

  @Override
  public PersonPeople getPersonDetails(Integer personApiId) {
    return api.getPeople().getPersonInfo(personApiId);
  }

  @Override
  public List<PersonCrew> getDirectors(Integer movieApiId) {
    return api
      .getMovies()
      .getCredits(movieApiId)
      .getCrew()
      .stream()
      .filter(crew -> crew.getJob().equalsIgnoreCase("Director"))
      .toList();
  }

  @Override
  public List<MovieDb> searchMovies(String query) {
    return api.getSearch().searchMovie(query, null, LANGUAGE, false, 1).getResults();
  }
}
