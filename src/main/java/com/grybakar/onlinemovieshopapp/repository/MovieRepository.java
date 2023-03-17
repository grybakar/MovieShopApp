package com.grybakar.onlinemovieshopapp.repository;

import com.grybakar.onlinemovieshopapp.model.Movie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

  Optional<Movie> findByTmdbId(Integer tmdbId);

  List<Movie> findByDirectorsFullNameIgnoreCase(String fullName);



  @Query("SELECT m FROM Movie m"
    + " LEFT JOIN FETCH m.cast c"
    + " LEFT JOIN FETCH m.directors d "
    + "WHERE lower(m.title)"
    + " LIKE lower(concat('%', :searchTerm,'%')) OR lower(c.fullName)"
    + " LIKE lower(concat('%', :searchTerm,'%')) OR lower(d.fullName)"
    + " LIKE lower(concat('%', :searchTerm,'%'))")
  List<Movie> searchByTitleOrActorOrDirector(@Param("searchTerm") String searchTerm);


}
