package com.example.literalura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosLibro(
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<DatosAutor> author,
        @JsonAlias("languages") List<String> language,
        @JsonAlias("download_count") Double downloads
) {
}
