package com.grybakar.onlinemovieshopapp.mapper;

import com.grybakar.onlinemovieshopapp.dto.MovieDto;
import com.grybakar.onlinemovieshopapp.model.Movie;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.MovieDb;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
  componentModel = "spring",
  unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MovieMapper {

  @Mapping(source = "title", target = "title")
  @Mapping(source = "overview", target = "description")
  @Mapping(source = "releaseDate", target = "releaseDate", dateFormat = "yyyy-MM-dd")
  @Mapping(source = "posterPath", target = "posterUrl", qualifiedByName = "imageUrl")
  @Mapping(source = "runtime", target = "duration")
  @Mapping(source = "id", target = "tmdbId")
  MovieDto toMovieDto(MovieDb movieDb);

  @Named("imageUrl")
  default String mapImageUrl(String posterPath) {
    TmdbApi config = new TmdbApi("eef3ab7073176d58c53449962c89fce2");
    String baseUrl = config.getConfiguration().getBaseUrl();
    List<String> posterSizes = config.getConfiguration().getPosterSizes();
    return baseUrl + posterSizes.get(3) + posterPath;
  }


  MovieDto toMovieDto(Movie movie);

  Movie toMovieEntity(MovieDto movieDto);


}
