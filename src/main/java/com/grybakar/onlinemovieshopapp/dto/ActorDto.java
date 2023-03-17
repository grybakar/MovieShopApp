package com.grybakar.onlinemovieshopapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActorDto {

  private Long id;
  private Integer tmdbId;
  private String fullName;
  private String birthday;
  private String profileUrl;
  private Float popularity;
  private String placeOfBirth;

}
