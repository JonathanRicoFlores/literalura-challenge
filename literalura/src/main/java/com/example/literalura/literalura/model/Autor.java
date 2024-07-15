package com.example.literalura.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String name;
    private Integer birthYear;
    private Integer deathYear;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL)
    private List<Libro> libros;

    public Autor() {
    }

    public Autor(DatosAutor datosAutor) {
        this.name = datosAutor.name();
        this.birthYear = Integer.valueOf(datosAutor.birthYear());
        this.deathYear = Integer.valueOf(datosAutor.deathYear());
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirth_year() {
        return birthYear;
    }

    public void setBirth_year(Integer birth_year) {
        this.birthYear = birth_year;
    }

    public Integer getDeath_year() {
        return deathYear;
    }

    public void setDeath_year(Integer death_year) {
        this.deathYear = death_year;
    }

    public List<Libro> getBooks() {
        return libros;
    }

    public void setBooks(List<Libro> books) {
        this.libros = books;
    }

    public Autor getFirstAuthor(DatosLibro datosLibro) {
        DatosAutor datosAutor = datosLibro.author().get(0);
        return new Autor(datosAutor);
    }

    @Override
    public String toString() {
        return "**** Author Info ****" +
                "\n\tName: " + name +
                "\n\tBirth year: " + birthYear +
                "\n\tDeath year: " + deathYear;
    }
}
