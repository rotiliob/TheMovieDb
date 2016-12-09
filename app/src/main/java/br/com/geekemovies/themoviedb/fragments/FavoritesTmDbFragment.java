package br.com.geekemovies.themoviedb.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import br.com.geekemovies.themoviedb.R;
import br.com.geekemovies.themoviedb.adapter.TMDBAdapter;
import br.com.geekemovies.themoviedb.dataBase.DatabaseEvent;
import br.com.geekemovies.themoviedb.dataBase.TmDbDao;
import br.com.geekemovies.themoviedb.interfaces.OnTmDbClick;
import br.com.geekemovies.themoviedb.model.Result;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesTmDbFragment extends Fragment {
    ListView mlistViewTmdb;
    List<Result> resultList;
    public FavoritesTmDbFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites_tm_db, container, false);// verificar se é fragment_tm_db_list

        mlistViewTmdb = (ListView)view.findViewById(R.id.list_movie);
        mlistViewTmdb.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(getActivity() instanceof OnTmDbClick){
                    Result result = (Result) mlistViewTmdb.getItemAtPosition(position);
                    ((OnTmDbClick) getActivity()).onTmDbClick(result);
                }
            }
        });
        updateList();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void updateList(){
        resultList = TmDbDao.getInstance(getActivity()
                .getApplication()
                .getApplicationContext()).getFavoriteTmDb();
        mlistViewTmdb.setAdapter(new TMDBAdapter(getActivity(), resultList));
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessagerEvent(DatabaseEvent event){
        updateList();
    }

}
