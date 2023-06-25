package com.project.movidle.Helpers;
import com.project.movidle.Movie;

import java.util.ArrayList;
import java.util.List;

public class Autocomplete {
    private List<Movie> dataset;

    public Autocomplete(List<Movie> _dataset) {
        dataset = _dataset;
    }

    public List<String> autocomplete(String prefix) {
        List<String> matches = new ArrayList<>();
        for (Movie word : dataset) {
            if (word.getTitle().toLowerCase().startsWith(prefix.toLowerCase())) {
                matches.add(word.getTitle());
            }
        }
        return matches;
    }
}
