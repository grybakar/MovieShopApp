package com.grybakar.onlinemovieshopapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieDto {

  private Long id;

  @JsonInclude(Include.NON_NULL)
  private Integer tmdbId;

  private String title;
  private String description;
  private String releaseDate;


  private Integer duration;
  private Float voteAverage;
  private String posterUrl;

  @JsonInclude(Include.NON_NULL)
  private BigDecimal price;


}
