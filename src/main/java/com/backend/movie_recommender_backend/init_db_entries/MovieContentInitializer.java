package com.backend.movie_recommender_backend.init_db_entries;

import com.backend.movie_recommender_backend.entities.Movie;
import com.backend.movie_recommender_backend.repositories.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.ToString;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MovieContentInitializer extends TableContentInitializer {
  private static final String MOVIES_METADATA = "movies_metadata.csv";

  @Override
  @Transactional
  public void initialize(final MovieRepository movieRepository) throws Exception {
    final URI movieCsvUri = TableContentInitializer.class.getResource(MOVIES_METADATA).toURI();
    final List<MovieRow> movieData = loadCsvData(movieCsvUri, MovieRow.class);
    final MoviePosterExtractor moviePosterExtractor = new MoviePosterExtractor();
    final String baseUrl = moviePosterExtractor.getBaseUrl();
    final List<Movie> moviesToWriteToDatabase = movieData.stream().map((movieRow) -> {
      Optional<String> posterUrl;
      try {
        posterUrl = moviePosterExtractor.getMoviePoster(movieRow.getImdb_id());
        posterUrl.ifPresent((p) -> System.out.println(p));
      } catch (HttpClientErrorException e) {
        posterUrl = Optional.empty();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      return new Movie(movieRow.getOriginal_title(), posterUrl.isPresent() ? baseUrl + posterUrl.get() : null);
    }).collect(Collectors.toList());
    movieRepository.saveAll(moviesToWriteToDatabase);
    System.out.println("Saved movies to database.");
  }

  @Data
  @ToString
  private static class MovieRow {
    String id;
    String imdb_id;
    String original_title;
    String overview;
  }
}
