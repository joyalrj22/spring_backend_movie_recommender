package com.backend.movie_recommender_backend.init_db_entries;

import com.backend.movie_recommender_backend.repositories.MovieRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class TableContentInitializer {

  protected <R> List<R> loadCsvData(final URI csvContainingTableContent, final Class<R> row) throws IOException {
    File csvFile = new File(csvContainingTableContent).getAbsoluteFile();
    CsvMapper csvMapper = CsvMapper.builder()
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .build();
    CsvSchema schema = CsvSchema.emptySchema().withHeader();
    MappingIterator<Map> rowsIterator = csvMapper
        .readerWithSchemaFor(Map.class)
        .with(schema)
        .readValues(csvFile);

    List<R> rows = new ArrayList<>();
    while (rowsIterator.hasNext()) {
      Map rowAsMap = rowsIterator.next();
      rows.add(csvMapper.convertValue(rowAsMap, row));
    }
    return rows;
  }

  public abstract void initialize(final MovieRepository movieRepository) throws Exception;

}
