package com.backend.movie_recommender_backend.init_db_entries;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.collect.ImmutableMap;
import lombok.Data;
import lombok.ToString;
import org.json.JSONObject;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MoviePosterExtractor {

  private static final String SECRETS_FILE = "secret.yml";
  private static final String TMDB_URL = "https://api.themoviedb.org/3/";
  private static final String CONFIG_ENDPOINT = "configuration";
  private static final String MOVIE_ENDPOINT = "movie/%s/images";

  public String getMoviePoster(final String imdb_id) throws IOException, URISyntaxException {
    final Map<String, String> requestParams = ImmutableMap.of("api_key", getTmdbApiKey());
    final JSONObject configDetails = makeTmdbGetRequest(CONFIG_ENDPOINT, requestParams).getJSONObject("images");
    final String baseUrl = configDetails.getString("base_url");
    final String posterSize = configDetails.getJSONArray("poster_sizes").getString(1);
    final String posterFilePath = getPosterFilePath(imdb_id, requestParams);
    final String imgUrl = baseUrl + posterSize + posterFilePath;

    return imgUrl;

  }

  private String getPosterFilePath(final String imdb_id, Map<String, String> requestParams) {
    JSONObject allImgDetailsJSONResponse = makeTmdbGetRequest(String.format(MOVIE_ENDPOINT, imdb_id), requestParams);
    List<HashMap> imgDetails = allImgDetailsJSONResponse.getJSONArray("posters");// filter it to have english then choose first one
    /**
     * {
     *     "posters": [
     *         {
     *             "aspect_ratio": 0.667,
     *             "height": 3000,
     *             "iso_639_1": "fr",
     *             "file_path": "/nLXYV4WmYUF4i8pX0iE0H1rltmf.jpg",
     *             "vote_average": 5.522,
     *             "vote_count": 6,
     *             "width": 2000
     *         },
     *         {
     *             "aspect_ratio": 0.667,
     *             "height": 1500,
     *             "iso_639_1": "de",
     *             "file_path": "/z46R8oShx61gXMrAmd7ptpVqNI0.jpg",
     *             "vote_average": 5.522,
     *             "vote_count": 4,
     *             "width": 1000
     *         },
     *         {
     *             "aspect_ratio": 0.667,
     *             "height": 3000,
     *             "iso_639_1": "en",
     *             "file_path": "/vgpXmVaVyUL7GGiDeiK1mKEKzcX.jpg",
     *             "vote_average": 5.398,
     *             "vote_count": 14,
     *             "width": 2000
     *         }
     *     ]
     * }
     */
  }

  private JSONObject makeTmdbGetRequest(final String endpoint, Map<String, ?> params) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
    HttpEntity<?> entity = new HttpEntity<>(headers);

    UriComponentsBuilder configRequestBuilder = UriComponentsBuilder.fromHttpUrl(TMDB_URL + endpoint);
    for (String param: params.keySet()) {
      configRequestBuilder = configRequestBuilder.queryParam(param, "{" + param + "}");
    }
    HttpEntity<String> response = restTemplate.exchange(
        configRequestBuilder.encode().toUriString(),
        HttpMethod.GET,
        entity,
        String.class,
        params
    );
    return new JSONObject(response.getBody());
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
