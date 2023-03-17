package com.grybakar.onlinemovieshopapp.populator;

import com.grybakar.onlinemovieshopapp.dto.MovieDto;
import com.grybakar.onlinemovieshopapp.service.TmdbApiService;
import info.movito.themoviedbapi.model.MovieDb;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MoviePopulator {

  private final TmdbApiService apiService;

  public void populateMovieData(MovieDto movieDto) {

    MovieDb movieDetails = apiService.getMovieDetails(movieDto.getTmdbId());

    movieDto.setDuration(movieDetails.getRuntime());
    movieDto.setPrice(BigDecimal.valueOf(9.99)); // DEFAULT PRICE ATM.
  }


}


