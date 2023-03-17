package com.grybakar.onlinemovieshopapp.service;

import com.grybakar.onlinemovieshopapp.model.Actor;
import com.grybakar.onlinemovieshopapp.repository.ActorRepository;
import info.movito.themoviedbapi.model.people.PersonCast;
import info.movito.themoviedbapi.model.people.PersonPeople;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ActorService {

  private final ActorRepository actorRepository;
  private final TmdbApiService apiService;


  public Set<Actor> getCastAndSaveToDb(Integer tmdbId) {

    List<PersonCast> cast = apiService.getCast(tmdbId);

    Set<Integer> castApiIds = cast
      .stream()
      .sorted(Comparator.comparingInt(PersonCast::getOrder))
      .limit(5)
      .map(PersonCast::getId)
      .collect(Collectors.toSet());

    List<Actor> actorsToSave = new ArrayList<>(); // hold the new actors that need to be saved to the database.
    Set<Actor> actors = new HashSet<>(); // is used to hold the actors that were found in the database or newly created ones.

    for (Integer castApiId : castApiIds) {
      Actor actor = actorRepository.findByTmdbId(castApiId);

      if (actor == null) {
        try {
          actor = createNewActor(castApiId);
          actorsToSave.add(actor);
        } catch (Exception e) {
          log.error("Error creating actor with tmdbId {}", castApiId, e);
          continue;
        }
      }
      actors.add(actor);
    }

    if (!actorsToSave.isEmpty()) {
      try {
        actorRepository.saveAll(actorsToSave);
        log.info("{} new actors saved to the database", actorsToSave.size());
      } catch (Exception e) {
        log.error("Error saving new actors to the database", e);
      }
    }

    return actors;
  }

  private Actor createNewActor(Integer apiId) {
    PersonPeople actorDetails = apiService.getPersonDetails(apiId);

    Actor actor = new Actor();

    actor.setTmdbId(actorDetails.getId());
    actor.setFullName(actorDetails.getName());

    if (actorDetails.getBirthday() != null && !actorDetails.getBirthday().isEmpty()) {
      actor.setBirthday(LocalDate.parse(actorDetails.getBirthday()));
    } else {
      actor.setBirthday(null);
    }

    if (actorDetails.getBirthplace() != null && !actorDetails.getBirthplace().isEmpty()) {
      actor.setPlaceOfBirth(actorDetails.getBirthplace());
    } else {
      actor.setPlaceOfBirth("N/A");
    }

    if (actorDetails.getProfilePath() != null && !actorDetails.getProfilePath().isEmpty()) {
      actor.setProfileUrl(actorDetails.getProfilePath());
    } else {
      actor.setProfileUrl("N/A");
    }

    actor.setPopularity(actorDetails.getPopularity());

    log.info("Created new actor: {}", actor);

    return actor;
  }


}


