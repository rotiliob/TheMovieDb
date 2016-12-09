package br.com.geekemovies.themoviedb.service;

import java.io.IOException;

import br.com.geekemovies.themoviedb.model.Movie;
import br.com.geekemovies.themoviedb.model.Result;
import br.com.geekemovies.themoviedb.model.SearchResult;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by andre.burgos on 02/12/2016.
 */

public class TMDBUtil {

    private static String KEY = "f4a5a876a9f2ad4b04a7308d9581a0ee";

    public static TMDBService getService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl originalHttpUrl = original.url();

                        HttpUrl url = originalHttpUrl.newBuilder()
                                .addQueryParameter("include_adult", "true")
                                .addQueryParameter("api_key", KEY)
                                .build();

                        // Request customization: add request headers
                        Request.Builder requestBuilder = original.newBuilder()
                                .url(url);

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(TMDBService.class);

     }
}