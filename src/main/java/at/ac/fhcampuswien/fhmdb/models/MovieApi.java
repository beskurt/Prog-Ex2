package at.ac.fhcampuswien.fhmdb.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Pattern;

public class MovieApi {


    // if you make this function happy you get a sexy Url
    public String createUrl(String query, String genre, String year, String rating) {
        StringBuilder url = new StringBuilder("http://localhost:8080/movies");

        // TODO: Make working Filters (no ors, we need ands).
        if (!query.isEmpty()  ) {               //TODO Disallow Numbers and Special character in query

            // ReplaceAll Whitespaces and Tabs with "%20"
            url.append("?query=").append(query.replaceAll("\\p{Blank}","%20")).append("&");
        }
        if (!genre.isEmpty()) {
            url.append("?genre=").append(genre).append("&");
        }
        if (!year.isEmpty()) {
            url.append("?year=").append(year).append("&");
        }

        if (!rating.isEmpty()) {
            url.append("?rating=").append(rating).append("&");
        }
        // Remove the trailing "&" character if there are any parameters
        if (url.charAt(url.length() - 1) == '&') {
            url.setLength(url.length() - 1);
        }
        System.out.println(url);
        return url.toString();
    }


    public List<Movie> getAllMoviesFromApi(String url) throws IOException {


        OkHttpClient client = new OkHttpClient();


        // Create a Request object
        Request request = new Request.Builder()
                .url(url)
                .build();
        // Send the request and get the response
        Response response = client.newCall(request).execute();

        // Check if the response was successful
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        // Get the JSON response as a string
        String jsonResponse = response.body().string();
        // Parse the JSON response using Gson
        Gson gson = new Gson();

        // The next Lines have learned Magic.
        // The String from the Server transforms into a List of Movies.
        // Just don´t touch it.
        Type movieListType = new TypeToken<List<Movie>>() {
        }.getType();
        List<Movie> movies = gson.fromJson(jsonResponse, movieListType);

        return movies;
    }


}
