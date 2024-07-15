package com.example.literalura.literalura.main;

import com.example.literalura.literalura.model.Autor;
import com.example.literalura.literalura.model.DatosLibro;
import com.example.literalura.literalura.model.Libro;
import com.example.literalura.literalura.model.Resultados;
import com.example.literalura.literalura.repository.AutorRepository;
import com.example.literalura.literalura.repository.LibroRepository;
import com.example.literalura.literalura.service.ConsumptionAPI;
import com.example.literalura.literalura.service.ConvertirData;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private Scanner sc = new Scanner(System.in);
    private ConvertirData convertirData = new ConvertirData();
    private ConsumptionAPI consumptionAPI = new ConsumptionAPI();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    List<Libro> libros;
    List<Autor> autores;

    public Main(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository= libroRepository;
        this.autorRepository = autorRepository;
    }

    public void showMenu() {
        final var menu = """
                \n\t**** Please, select an option ****
                \t1 - Search book by title
                \t2 - List registered books
                \t3 - List registered authors
                \t4 - List authors alive in a given year
                \t5 - List books by language
                \n\t0 - Exit
                """;
        var option = -1;
        System.out.println("****************************************");
        while (option != 0) {
            System.out.println(menu);
            System.out.print("Option: ");
            option = sc.nextInt();
            sc.nextLine();
            switch (option) {
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    listRegisteredBooks();
                    break;
                case 3:
                    listRegisteredAuthors();
                    break;
                case 4:
                    listAuthorsAliveInYear();
                    break;
                case 5:
                    listBooksByLanguage();
                    break;
                case 0:
                    System.out.println("ending application...");
                    break;
                default:
                    System.out.println("Invalid option, please, try again");
                    break;
            }
        }
        System.out.println("****************************************");
    }

    private void searchBookByTitle() {
        System.out.print("Search book by title...Please, enter title: ");
        String inTitle = sc.nextLine();
        var json = consumptionAPI.getData(inTitle.replace(" ", "%20"));
        //System.out.println("json: " + json);
        var data = convertirData.getData(json, Resultados.class);
        //System.out.println("data: " + data);
        if (data.results().isEmpty()) {
            System.out.println("Book not found");
        } else {
            DatosLibro datosLibro = data.results().get(0);
            //System.out.println("bookData: " + bookData);
            Libro libro = new Libro(datosLibro);
            //System.out.println("book: " + book);
            Autor autor = new Autor().getFirstAuthor(datosLibro);
            //System.out.println("author: " + author);
            saveData(libro, autor);
        }
    }

    private void saveData(Libro libro, Autor autor) {
        Optional<Libro> bookFound = libroRepository.findByTitleContains(libro.getTitle());
        //System.out.println("bookFound: " + bookFound);
        if (bookFound.isPresent()) {
            System.out.println("this book was already registered");
        } else {
            try {
                libroRepository.save(libro);
                System.out.println("book registered");
            } catch (Exception e) {
                System.out.println("Error message: " + e.getMessage());
            }
        }

        Optional<Autor> authorFound = autorRepository.findByNameContains(autor.getName());
        //System.out.println("authorFound: " + authorFound);
        if (authorFound.isPresent()) {
            System.out.println("this author was already registered");
        } else {
            try {
                autorRepository.save(autor);
                System.out.println("author registered");
            } catch (Exception e) {
                System.out.println("Error message: " + e.getMessage());
            }
        }
    }

    private void listRegisteredBooks() {
        System.out.println("List registered books\n---------------------");
        libros = libroRepository.findAll();
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitle))
                .forEach(System.out::println);
    }

    private void listRegisteredAuthors() {
        System.out.println("List registered authors\n-----------------------");
        autores = autorRepository.findAll();
        autores.stream()
                .sorted(Comparator.comparing(Autor::getName))
                .forEach(System.out::println);
    }

    private void listAuthorsAliveInYear() {
        System.out.print("List authors alive in a given year...Please, enter year: ");
        Integer year = Integer.valueOf(sc.nextLine());
        autores = autorRepository
                .findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(year, year);
        if (autores.isEmpty()) {
            System.out.println("Authors alive not found");
        } else {
            autores.stream()
                    .sorted(Comparator.comparing(Autor::getName))
                    .forEach(System.out::println);
        }
    }

    private void listBooksByLanguage() {
        System.out.println("List books by language\n----------------------");
        System.out.println("""
                \n\t---- Please, select a language ----
                \ten - English
                \tes - Spanish
                \tfr - French
                \tpt - Portuguese
                """);
        String lang = sc.nextLine();
        libros = libroRepository.findByLanguageContains(lang);
        if (libros.isEmpty()) {
            System.out.println("Books by language selected not found");
        } else {
            libros.stream()
                    .sorted(Comparator.comparing(Libro::getTitle))
                    .forEach(System.out::println);
        }
    }
}