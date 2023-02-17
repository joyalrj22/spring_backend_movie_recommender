package com.backend.movie_recommender_backend.init_db_entries;

import lombok.Data;
import lombok.ToString;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieContentInitializer extends TableContentInitializer {
  private static final String MOVIES_METADATA = "movies_metadata.csv";

  @Override
  public void initialize() throws Exception {
    final URI movieCsvUri = TableContentInitializer.class.getResource(MOVIES_METADATA).toURI();
    final List<MovieRow> movieData = loadCsvData(movieCsvUri, MovieRow.class);
    System.out.println(new MoviePosterExtractor().getMoviePoster(movieData.get(1).getImdb_id()));
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
