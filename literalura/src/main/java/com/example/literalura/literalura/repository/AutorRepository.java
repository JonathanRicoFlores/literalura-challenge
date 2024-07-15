package com.example.literalura.literalura.repository;

import com.example.literalura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository <Autor, Long> {
    Optional<Autor> findByNameContains(String name);
    List<Autor> findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(Integer birthYear, Integer deathYear);
}