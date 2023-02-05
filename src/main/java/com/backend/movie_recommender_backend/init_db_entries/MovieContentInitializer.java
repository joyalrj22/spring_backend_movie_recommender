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
  // https://stackoverflow.com/questions/44342276/how-to-push-code-to-github-hiding-the-api-keys
  private static final String TMDB_API_KEY = "7a858c80b3c2d2d7e52cc7c183306670";
  private static final String TMDB_HTTP_QUERY_TEMPLATE = "https://api.themoviedb.org/3/find/tt0111161?api_key=%s&external_source=%s";
  private static final String TMDB_URL = "https://api.themoviedb.org/3/find/tt0111161";

  @Override
  public void initialize() throws Exception {
    final URI movieCsvUri = TableContentInitializer.class.getResource(MOVIES_METADATA).toURI();
    final List<MovieRow> movieData = loadCsvData(movieCsvUri, MovieRow.class);
  }

  private void getMoviePoster(final String imdb_id) throws IOException {
    URL url = new URL(TMDB_URL);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    Map<String, String> parameters = new HashMap<>();
    parameters.put("api_key", TMDB_API_KEY);
    parameters.put("external_source", imdb_id);
  }

  @Data
  @ToString
  private static class MovieRow {
    String id;
    String imdb_id;
    String original_title;
    String overview;
  }

  public static void main(String[] args) throws Exception {
    new MovieContentInitializer().initialize();

  }
}
