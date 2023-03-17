package com.grybakar.onlinemovieshopapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DirectorDto {

  private Long id;
  private Integer tmdbId;
  private String fullName;

}
