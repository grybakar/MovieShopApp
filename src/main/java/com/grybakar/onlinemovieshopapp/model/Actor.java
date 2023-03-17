package com.grybakar.onlinemovieshopapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class Actor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "tmdb_id")
  private Integer tmdbId;

  @Column(name = "full_name")
  private String fullName;

  private LocalDate birthday;

  @Column(name = "profile_url")
  private String profileUrl;

  private Float popularity;

  @Column(name = "place_of_birth")
  private String placeOfBirth;

  @ManyToMany(mappedBy = "cast", fetch = FetchType.LAZY)
  private Set<Movie> movies = new HashSet<>();


}
