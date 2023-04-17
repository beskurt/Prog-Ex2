package at.ac.fhcampuswien.fhmdb.models;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Movie {
    private final String id;
    private final String title;
    private final List<Genre> genres;
    private final int releaseYear;
    private final String description;
    private final String imgUrl;
    private final int lengthInMinutes;
    private final List<String> directors;
    private final List<String> writers;
    private final List<String> mainCast;
    private final double rating;

    public Movie(String id, String title, List<Genre> genres, int releaseYear, String description, String imgUrl, int lengthInMinutes, List<String> directors, List<String> writers, List<String> mainCast, double rating) {
        this.id = id;
        this.title = title;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.description = description;
        this.imgUrl = imgUrl;
        this.lengthInMinutes = lengthInMinutes;
        this.directors = directors;
        this.writers = writers;
        this.mainCast = mainCast;
        this.rating = rating;
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Movie movie = (Movie) other;
        return releaseYear == movie.releaseYear &&
                lengthInMinutes == movie.lengthInMinutes &&
                Double.compare(movie.rating, rating) == 0 &&
                Objects.equals(id, movie.id) &&
                Objects.equals(title, movie.title) &&
                Objects.equals(genres, movie.genres) &&
                Objects.equals(description, movie.description) &&
                Objects.equals(imgUrl, movie.imgUrl) &&
                Objects.equals(directors, movie.directors) &&
                Objects.equals(writers, movie.writers) &&
                Objects.equals(mainCast, movie.mainCast);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public String getId() {
        return id;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public List<String> getdirectors() {
        return directors;
    }

    public List<String> getWriters() {
        return writers;
    }

    public List<String> getMainCast() {
        return mainCast;
    }

    public double getRating() {
        return rating;
    }







    public static List<Movie> initializeMovies() throws IOException {

        List<Movie> movies;

        // ".get()" == returns a List<Movie> containing all(!!!!) movies the Api gave us.
        MovieApi movieApi = new MovieApi();
        movies = movieApi.getAllMoviesFromApi("http://localhost:8080/movies");
        return movies;
    }
}
