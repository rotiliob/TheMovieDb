package br.com.geekemovies.themoviedb.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.com.geekemovies.themoviedb.R;
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
        mlistViewTmdb = TmDbDao.getInstance(getActivity())
                .get
        updateList();
        return view;
    }

    private void updateList(){

    }
}
