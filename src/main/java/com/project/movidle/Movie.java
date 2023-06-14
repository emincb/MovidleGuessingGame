package com.project.movidle;

public class Movie {
    private String title;
    private String year;
    private String genre;
    private String origin;
    private String director;
    private String star;

    public Movie(String title, String year, String genre, String origin, String director, String star) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.origin = origin;
        this.director = director;
        this.star = star;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDirector() {
        return director;
    }

    public String getStar() {
        return star;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", genre='" + genre + '\'' +
                ", origin='" + origin + '\'' +
                ", director='" + director + '\'' +
                ", star='" + star + '\'' +
                '}';
    }

    public String getAttributeValue(String attribute) {
        if (attribute.equalsIgnoreCase("Title")) {
            return title;
        } else if (attribute.equalsIgnoreCase("Year")) {
            return year;
        } else if (attribute.equalsIgnoreCase("Genre")) {
            return genre;
        } else if (attribute.equalsIgnoreCase("Origin")) {
            return origin;
        } else if (attribute.equalsIgnoreCase("Director")) {
            return director;
        } else if (attribute.equalsIgnoreCase("Star")) {
            return star;
        } else {
            return "";
        }
    }

}
