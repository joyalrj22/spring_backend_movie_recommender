package com.backend.movie_recommender_backend.init_db_entries;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Data;
import lombok.ToString;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

public class MoviePosterExtractor {

  private static final String SECRETS_FILE = "secret.yml";
  private static final String TMDB_URL = "https://api.themoviedb.org/3/";
  private static final String CONFIG_ENDPOINT = "configuration";
  private static final String MOVIE_ENDPOINT = "movie/%s/images";

  private final String apiKey;

  public MoviePosterExtractor() throws URISyntaxException, IOException {
    apiKey = getTmdbApiKey();
  }

  public String getBaseUrl() throws URISyntaxException, IOException, ParseException {
   final JsonObject configDetails = makeTmdbGetRequest(CONFIG_ENDPOINT, ImmutableMap.of("api_key", apiKey)).get("images").getAsJsonObject();
   final String baseUrl = configDetails.get("base_url").getAsString();
   final String posterSize = configDetails.get("poster_sizes").getAsJsonArray().get(1).getAsString();
   return baseUrl + posterSize;
 }

  public Optional<String> getMoviePoster(final String imdb_id) throws IOException, URISyntaxException, ParseException {
    final Optional<String> posterFilePath = getPosterFilePath(imdb_id, ImmutableMap.of("api_key", apiKey, "include_image_language", "en"));
    return posterFilePath.map((fp) -> posterFilePath.get());
  }

  private Optional<String> getPosterFilePath(final String imdb_id, Map<String, String> requestParams) throws ParseException {
    final JsonObject allImgDetailsJSONResponse = makeTmdbGetRequest(String.format(MOVIE_ENDPOINT, imdb_id), requestParams);
    final JsonArray postersArray = allImgDetailsJSONResponse.get("posters").getAsJsonArray();
    return postersArray.isEmpty() ? Optional.empty() : Optional.of(postersArray.get(0).getAsJsonObject().get("file_path").getAsString());
  }

  private JsonObject makeTmdbGetRequest(final String endpoint, Map<String, ?> params) throws ParseException {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    HttpEntity<?> entity = new HttpEntity<>(headers);

    UriComponentsBuilder configRequestBuilder = UriComponentsBuilder.fromHttpUrl(TMDB_URL + endpoint);
    for (String param: params.keySet()) {
      configRequestBuilder = configRequestBuilder.queryParam(param, "{" + param + "}");
    }
    String body =  restTemplate.exchange(
        configRequestBuilder.encode().toUriString(),
        HttpMethod.GET,
        entity,
        String.class,
        params
    ).getBody();
    return new JsonParser().parse(body).getAsJsonObject();
  }

  private String getTmdbApiKey() throws URISyntaxException, IOException {
    final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    final URI secretsUri = MoviePosterExtractor.class.getResource(SECRETS_FILE).toURI();
    return mapper.readValue(new File(secretsUri).getAbsoluteFile(), YamlSecrets.class).getTMDB_API_KEY();
  }

  @Data
  @ToString
  private static class YamlSecrets {
    private String TMDB_API_KEY;
  }
}
