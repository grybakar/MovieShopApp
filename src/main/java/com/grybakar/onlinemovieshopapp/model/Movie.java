package com.grybakar.onlinemovieshopapp.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "tmdb_id")
  private Integer tmdbId;

  private String title;
  private String description;

  @Column(name = "release_date")
  private LocalDate releaseDate;

  // TODO ADD GENRE AS MANY TO MANY RELATIONSHIP AND POPULATE DATA FROM API

  private Integer duration;

  @Column(name = "vote_average")
  private Float voteAverage;

  @Column(name = "poster_url")
  private String posterUrl;

  private BigDecimal price;

  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE
  }, fetch = FetchType.LAZY)
  @JoinTable(
    name = "movie_director",
    joinColumns = @JoinColumn(name = "movie_id"),
    inverseJoinColumns = @JoinColumn(name = "director_id")
  )
  private Set<Director> directors = new HashSet<>();

  @ManyToMany(cascade = {
    CascadeType.PERSIST,
    CascadeType.MERGE
  }, fetch = FetchType.LAZY)
  @JoinTable(
    name = "movie_actor",
    joinColumns = @JoinColumn(name = "movie_id"),
    inverseJoinColumns = @JoinColumn(name = "actor_id")
  )
  private Set<Actor> cast = new HashSet<>();


  // Cannot invoke "java.util.Set.add(Object)" because "this.cast" is null -> Lazy init pattern helped
  public void addActor(Actor actor) {
    if (cast == null) {
      cast = new HashSet<>();
    }
    cast.add(actor);
  }

  public void removeActor(Actor actor) {
    cast.remove(actor);
    actor.getMovies().remove(this);
  }

}
