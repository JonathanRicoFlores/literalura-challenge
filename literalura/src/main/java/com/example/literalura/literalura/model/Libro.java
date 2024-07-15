package com.example.literalura.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String title;
    private String author;
    private String language;
    private Double downloads;

    public Libro() {
    }

    public Libro(DatosLibro datosLibro) {
        this.title = datosLibro.title();
        this.author = getFirstAuthor(datosLibro).getName();
        this.language = getFirstLanguage(datosLibro);
        this.downloads = datosLibro.downloads();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Double getDownloads() {
        return downloads;
    }

    public void setDownloads(Double downloads) {
        this.downloads = downloads;
    }

    public Autor getFirstAuthor(DatosLibro datosLibro) {
        DatosAutor datosAutor = datosLibro.author().get(0);
        return new Autor(datosAutor);
    }

    public String getFirstLanguage(DatosLibro datosLibro) {
        return datosLibro.language().get(0);
    }

    @Override
    public String toString() {
        return "**** Book Info ****" +
                "\n\tTitle: " + title +
                "\n\tAuthor: " + author +
                "\n\tLanguage: " + language +
                "\n\tDownloads: " + downloads;
    }
}
