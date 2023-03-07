package com.grybakar.onlinemovieshopapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
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


}
