package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.MovieApi;
import at.ac.fhcampuswien.fhmdb.models.SortedState;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HomeController implements Initializable {


    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXComboBox releaseYearComboBox;


    @FXML
    public JFXComboBox ratingComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies;

    protected ObservableList<Movie> observableMovies = FXCollections.observableArrayList();

    protected SortedState sortedState;
    @FXML
    private Label countLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initializeState();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        initializeLayout();
    }

    public void initializeState() throws IOException {
        allMovies = Movie.initializeMovies();
        extractDropdownMenuItemsFromApi(allMovies);
        observableMovies.clear();
        observableMovies.addAll(allMovies); // add all movies to the observable list
        sortedState = SortedState.NONE;


    }

    static ArrayList<Double> ratings = new ArrayList<Double>();
    static ArrayList<Integer> years = new ArrayList<Integer>();


    public static void extractDropdownMenuItemsFromApi(List<Movie> movies) {


        for (int i = 0; i < movies.size(); i++) {


            years.add(movies.get(i).getReleaseYear());
            ratings.add(movies.get(i).getRating());
        }


    }

    public void initializeLayout() {


        movieListView.setItems(observableMovies);   // set the items of the listview to the observable list
        movieListView.setCellFactory(movieListView -> new MovieCell()); // apply custom cells to the listview


        // This displays a counter showing how many movies are shown right now.
        countLabel.setText("Showing " + movieListView.getItems().size() + " items");
        movieListView.getItems().addListener((ListChangeListener<String>) c -> {
            int count = movieListView.getItems().size();
            countLabel.setText("Showing " + count + " items");
        });


        Object[] genres = Genre.values();   // get all genres
        genreComboBox.getItems().addAll(genres);    // add all genres to the combobox
        genreComboBox.getItems().add("No filter");  // add "no filter" to the combobox
        genreComboBox.setPromptText("Filter by Genre");


        // Released Year


        releaseYearComboBox.getItems().addAll(years);    // add all genres to the combobox
        releaseYearComboBox.getItems().add("No filter");  // add "no filter" to the combobox
        releaseYearComboBox.setPromptText("Filter by Release Year");


        // RATING


        ratingComboBox.getItems().addAll(ratings);    // add all genres to the combobox
        ratingComboBox.getItems().add("No filter");  // add "no filter" to the combobox
        ratingComboBox.setPromptText("Filter by Rating");

    }

    public void sortMovies() {
        if (sortedState == SortedState.NONE || sortedState == SortedState.DESCENDING) {
            sortMovies(SortedState.ASCENDING);
        } else if (sortedState == SortedState.ASCENDING) {
            sortMovies(SortedState.DESCENDING);
        }
    }

    // sort movies based on sortedState
    // by default sorted state is NONE
    // Afterward it switches between ascending and descending
    public void sortMovies(SortedState sortDirection) {
        if (sortDirection == SortedState.ASCENDING) {
            observableMovies.sort(Comparator.comparing(Movie::getTitle));
            sortedState = SortedState.ASCENDING;
        } else {
            observableMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
            sortedState = SortedState.DESCENDING;
        }
    }

    MovieApi movieApi = new MovieApi();


    public void applyAllFilters() {

        String searchQuery;
        Object genre;
        Object selectedYear;
        Object selectedRating;

        //The following ~20 Lines check if an input is empty or is set to "No filter"
        // If it is empty (or is set to "No filter") it gets changed to: "" to be able to pass it directly to the urlBuilder function in MovieApi.
        //
        if (searchField.getText().trim().toLowerCase().isEmpty()) {
            searchQuery = "";
        } else
            searchQuery = searchField.getText().trim().toLowerCase();
        if (genreComboBox.getSelectionModel().getSelectedItem() == null || genreComboBox.getSelectionModel().getSelectedItem() == "No filter") {
            genre = "";
        } else
            genre = genreComboBox.getSelectionModel().getSelectedItem();
        if (releaseYearComboBox.getSelectionModel().getSelectedItem() == null || releaseYearComboBox.getSelectionModel().getSelectedItem() == "No filter") {
            selectedYear = "";
        } else
            selectedYear = releaseYearComboBox.getSelectionModel().getSelectedItem();
        if (ratingComboBox.getSelectionModel().getSelectedItem() == null || ratingComboBox.getSelectionModel().getSelectedItem() == "No filter") {
            selectedRating = "";
        } else
            selectedRating = ratingComboBox.getSelectionModel().getSelectedItem();


        String url = movieApi.createUrl(searchQuery, genre.toString(), selectedYear.toString(), selectedRating.toString());


        observableMovies.clear();


        try {
            observableMovies.addAll(movieApi.getAllMoviesFromApi(url));
            sortMovies(sortedState);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void searchBtnClicked(ActionEvent actionEvent) {

        applyAllFilters();
        // applyAllFiltersViaApi(movieApi.createUrl(searchQuery,genre.toString(),selectedYear,selectedRating));


        sortMovies(sortedState);
    }


    public void sortBtnClicked(ActionEvent actionEvent) {
        sortMovies();
    }

}

//TODO Weiters sind folgende Methoden unter Verwendung von Streams im Controller zu implementieren:
//         String getMostPopularActor(List<Movie> movies): gibt jene Person zurück, die am
//        öftesten im mainCast der übergebenen Filme vorkommt.
//         int getLongestMovieTitle(List<Movie> movies): filtert auf den längsten Filmtitel der
//        übergebenen Filme und gibt die Anzahl der Buchstaben des Titels zurück
//         long countMoviesFrom(List<Movie> movies, String director): gibt die Anzahl der
//        Filme eines bestimmten Regisseurs zurück.
//         List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int
//        endYear): gibt jene Filme zurück, die zwischen zwei gegebenen Jahren veröffentlicht
//        wurden.
//        Die Funktionen sind ausschließlich mit Streams zu implementieren (keine Schleifen!)



    String getMostPopularActor(List<Movie> movies) {
        return null;
    }

    int getLongestMovieTitle(List<Movie> movies) {

        return 0;
    }

    long countMoviesFrom(List<Movie> movies, String director) {
        return 0;
    }

    List<Movie> getMoviesBetweenYears(List<Movie> movies, int startYear, int endYear) {

        return movies;
    }
}

