package at.ac.fhcampuswien.fhmdb.models;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class MovieApi {



    public List<Movie> getAllMoviesFromApi() throws IOException {
        // Create an OkHttpClient instance
        OkHttpClient client = new OkHttpClient();


        String year = "";
        String query = "";

        String genre = "";
        String rating = "";

        // Create a Request object
        Request request = new Request.Builder()
                .url("http://localhost:8080/movies?query=" + query + "&genre=" + genre + "&releaseYear=" + year + "&ratingFrom=" + rating)
                .build();

        // Send the request and get the response
        Response response = client.newCall(request).execute();

        // Check if the response was successful
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }

        // Get the JSON response as a string
        String jsonResponse = response.body().string();
        System.out.println(jsonResponse);
        // Parse the JSON response using Gson
        Gson gson = new Gson();
        Type movieListType = new TypeToken<List<Movie>>() {
        }.getType();
        List<Movie> movies = gson.fromJson(jsonResponse, movieListType);

        return movies;
    }



    //TODO: Take querys and create url to get data


}
