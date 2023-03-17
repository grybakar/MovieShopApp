package com.grybakar.onlinemovieshopapp.repository;

import com.grybakar.onlinemovieshopapp.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {

  Actor findByTmdbId(Integer tmdbId);



}
