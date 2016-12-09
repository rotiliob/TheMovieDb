package br.com.geekemovies.themoviedb.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import br.com.geekemovies.themoviedb.R;
import br.com.geekemovies.themoviedb.activitys.DetailTmDbAct;
import br.com.geekemovies.themoviedb.activitys.MainActivity;
import br.com.geekemovies.themoviedb.adapter.TMDBAdapter;
import br.com.geekemovies.themoviedb.interfaces.OnTmDbClick;
import br.com.geekemovies.themoviedb.model.Result;
import br.com.geekemovies.themoviedb.model.SearchResult;
import br.com.geekemovies.themoviedb.service.TMDBService;
import br.com.geekemovies.themoviedb.service.TMDBUtil;
import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 */
public class TmDbListFragment extends Fragment implements SearchView.OnQueryTextListener {

    ListView mListMovie;
    TMDBService mTmdbService;
    public TmDbListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tm_db_list, container, false);

        mListMovie = (ListView) view.findViewById(R.id.list_movie);
        mListMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getActivity() instanceof OnTmDbClick) {
                    Result result = (Result) mListMovie.getItemAtPosition(position);
                    ((OnTmDbClick) getActivity()).onTmDbClick(result);
                }
            }
        });
        mTmdbService = TMDBUtil.getService();
        EventBus.getDefault().register(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
    }

    public void setAdapter(List<Result> results ){
        mListMovie.setAdapter(new TMDBAdapter(getActivity(), results));
    }

    private void loadMovie(String titulo) {
        mTmdbService.searchMovie(titulo).enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, retrofit2.Response<SearchResult> response) {
                List<Result> results = response.body().getResults();
                EventBus.getDefault().postSticky(results);
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        loadMovie(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onMessageEvent(List<Result>event) {
        setAdapter(event);
    }
}
