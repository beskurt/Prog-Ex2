package at.ac.fhcampuswien.fhmdb.models;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Movie {
    //    private final String title;
    //    private final String description;
    //    private final List<Genre> genres;
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

    public static List<Movie> initializeMovies() throws IOException {


        List<Movie> movies;


        // ".get()" == returns a List<Movie> containing all(!!!!) movies the Api gave us.
        MovieApi movieApi = new MovieApi();
        movies = movieApi.getAllMoviesFromApi();


        //        movies.add(new Movie(
//                "Life Is Beautiful",
//                "When an open-minded Jewish librarian and his son become victims of the Holocaust, he uses a perfect mixture of will, humor, and imagination to protect his son from the dangers around their camp.",
//                Arrays.asList(Genre.DRAMA, Genre.ROMANCE)));
//        movies.add(new Movie(
//                "The Usual Suspects",
//                "A sole survivor tells of the twisty events leading up to a horrific gun battle on a boat, which begin when five criminals meet at a seemingly random police lineup.",
//                Arrays.asList(Genre.CRIME, Genre.DRAMA, Genre.MYSTERY)));
//        movies.add(new Movie(
//                "Puss in Boots",
//                "An outlaw cat, his childhood egg-friend, and a seductive thief kitty set out in search for the eggs of the fabled Golden Goose to clear his name, restore his lost honor, and regain the trust of his mother and town.",
//                Arrays.asList(Genre.COMEDY, Genre.FAMILY, Genre.ANIMATION)));
//        movies.add(new Movie(
//                "Avatar",
//                "A paraplegic Marine dispatched to the moon Pandora on a unique mission becomes torn between following his orders and protecting the world he feels is his home.",
//                Arrays.asList(Genre.ANIMATION, Genre.DRAMA, Genre.ACTION)));
//        movies.add(new Movie(
//                "The Wolf of Wall Street",
//                "Based on the true story of Jordan Belfort, from his rise to a wealthy stock-broker living the high life to his fall involving crime, corruption and the federal government.",
//                Arrays.asList(Genre.DRAMA, Genre.ROMANCE, Genre.BIOGRAPHY)));

        return movies;
    }
}
