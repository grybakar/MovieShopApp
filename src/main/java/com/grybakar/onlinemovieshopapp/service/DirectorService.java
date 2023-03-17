package com.grybakar.onlinemovieshopapp.service;

import com.grybakar.onlinemovieshopapp.model.Director;
import com.grybakar.onlinemovieshopapp.repository.DirectorRepository;
import info.movito.themoviedbapi.model.people.PersonCrew;
import info.movito.themoviedbapi.model.people.PersonPeople;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class DirectorService {

  public final TmdbApiService apiService;
  public final DirectorRepository directorRepository;

  public Set<Director> getDirectorsAndSaveToDb(Integer tmdbId) {
    List<PersonCrew> movieDirectors = apiService.getDirectors(tmdbId);

    Set<Integer> directorApiIds = movieDirectors
      .stream()
      .map(PersonCrew::getId)
      .collect(Collectors.toSet());

    List<Director> directorsToSave = new ArrayList<>(); // hold the new directors that need to be saved to the database.
    Set<Director> directors = new HashSet<>(); // is used to hold the directors that were found in the database or newly created ones.

    for (Integer directorApiId : directorApiIds) {
      Director director = directorRepository.findByTmdbId(directorApiId);

      if (director == null) {
        try {
          director = createNewDirector(directorApiId);
          directorsToSave.add(director);
        } catch (Exception e) {
          log.error("Error creating director with tmdbId {}", directorApiId, e);
          continue;
        }
      }
      directors.add(director);
    }

    if (!directorsToSave.isEmpty()) {
      try {
        directorRepository.saveAll(directorsToSave);
        log.info("{} new directors saved to the database", directorsToSave.size());
      } catch (Exception e) {
        log.error("Error saving new directors to the database", e);
      }
    }

    return directors;
  }

  private Director createNewDirector(Integer apiId) {
    PersonPeople directorDetails = apiService.getPersonDetails(apiId);

    Director director = Director.builder()
      .tmdbId(directorDetails.getId())
      .fullName(directorDetails.getName())
      .build();

    log.info("Created new director: {}", director);

    return director;
  }
}






