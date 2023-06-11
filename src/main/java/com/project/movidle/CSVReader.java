package com.project.movidle;
import java.nio.charset.StandardCharsets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    public static List<Movie> readMovies() {
        String csvFile = "C:\\Users\\eminb\\IdeaProjects\\Movidle\\src\\main\\resources\\com\\project\\movidle\\imdb_top_250.csv";
        String line;
        String csvSplitBy = ";";

        List<Movie> movies = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile,StandardCharsets.UTF_8))) {
            while ((line = br.readLine()) != null) {
                String[] rowData = line.split(csvSplitBy);
                String title = rowData[1];
                String year =  rowData[2];
                String genre = rowData[3];
                String origin = rowData[4];
                String director = rowData[5];
                String star = rowData[6];

                Movie movie = new Movie(title, year, genre, origin, director, star);
                movies.add(movie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movies;
    }
}