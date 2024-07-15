package com.example.literalura.literalura.repository;

import com.example.literalura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByTitleContains(String title);
    List<Libro> findByLanguageContains(String language);
}
