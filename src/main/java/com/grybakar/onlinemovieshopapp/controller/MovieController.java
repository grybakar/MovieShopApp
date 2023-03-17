package com.grybakar.onlinemovieshopapp.controller;


import com.grybakar.onlinemovieshopapp.dto.MovieDto;
import com.grybakar.onlinemovieshopapp.service.MovieService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/movies")
@AllArgsConstructor
public class MovieController {

  private final MovieService movieService;

  @PostMapping
  public ResponseEntity<MovieDto> saveMovie(@RequestBody MovieDto movieDto) {

    MovieDto createdMovieDto = movieService.saveMovie(movieDto);
    return ResponseEntity
      .created(movieService.createMovieURI(createdMovieDto.getId()))
      .body(createdMovieDto);
  }

  @GetMapping("admin")
  public ResponseEntity<List<MovieDto>> searchMoviesInApi(@RequestParam String query) {
    return ResponseEntity.ok().body(movieService.searchMoviesInApi(query));
  }

  @GetMapping
  public ResponseEntity<List<MovieDto>> searchMovies() {
    return ResponseEntity.ok(movieService.searchMoviesInDatabase());
  }


  @GetMapping("{id}")
  public ResponseEntity<MovieDto> searchMovieById(@PathVariable Long id) {
    return ResponseEntity.ok(movieService.searchMovieById(id));
  }

  @GetMapping("search")
  public ResponseEntity<List<MovieDto>> searchMoviesByDirectorsFullName(@RequestParam String fullName) {
    return ResponseEntity.ok().body(movieService.searchMoviesByDirectorFullName(fullName));
  }

  @GetMapping("searchAll")
  public ResponseEntity<List<MovieDto>> searchMovieByTitleOrActorOrDirector(@RequestParam String query) {
    return ResponseEntity.ok().body(movieService.searchMovieByTitleOrActorOrDirector(query));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deletePosition(@PathVariable(name = "id") Long id) {
    movieService.deleteMovie(id);
    return ResponseEntity.noContent().build();
  }

}
