package br.com.geekemovies.themoviedb.service;


import br.com.geekemovies.themoviedb.model.Movie;
import br.com.geekemovies.themoviedb.model.SearchResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBService {
    @GET("movie/{id}")
    Call<Movie> getMovie(@Path("id") String movieId);

    @GET("search/movie")
    Call<SearchResult> searchMovie(@Query("query") String query);
}
