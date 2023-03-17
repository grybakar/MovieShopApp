package com.grybakar.onlinemovieshopapp.service;

import info.movito.themoviedbapi.model.MovieDb;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.people.PersonCrew;
import info.movito.themoviedbapi.model.people.PersonPeople;
import java.util.List;

public interface TmdbApiService {

  MovieDb getMovieDetails(Integer movieApiId);

  List<PersonCast> getCast(Integer movieApiId);

  PersonPeople getPersonDetails(Integer personApiId);

  List<PersonCrew> getDirectors(Integer movieApiId);

  List<MovieDb> searchMovies(String query);


}
