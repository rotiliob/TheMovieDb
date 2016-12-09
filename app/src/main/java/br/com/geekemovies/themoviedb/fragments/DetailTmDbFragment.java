package br.com.geekemovies.themoviedb.fragments;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import br.com.geekemovies.themoviedb.R;
import br.com.geekemovies.themoviedb.dataBase.DatabaseEvent;
import br.com.geekemovies.themoviedb.dataBase.TmDbDao;
import br.com.geekemovies.themoviedb.model.Result;

public class DetailTmDbFragment extends Fragment {

    TextView originalTitle    ;
    TextView releaseDate      ;
    TextView original_language;
    TextView overViewer       ;

    FloatingActionButton floatingActionButton;
    Result result;
    TmDbDao tmDbDao;
    boolean isFavorite;

    public static DetailTmDbFragment newInstance(Result result){
        Bundle bundle = new Bundle();
        bundle.putSerializable("result",result);

        DetailTmDbFragment detailTmDbFragment = new DetailTmDbFragment();
        detailTmDbFragment.setArguments(bundle);
        return detailTmDbFragment;
    }

    public DetailTmDbFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_tm_db, container, false);

        Result result;
        if (getResources().getBoolean(R.bool.tablet)) {
            result = (Result) getArguments().get("result");
        }else {
            result = (Result) getActivity().getIntent().getSerializableExtra("result");
        }


        if (getResources().getBoolean(R.bool.phone)) {
             Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            toolbar.setTitle(result.getTitle());
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   seveOrRemoveFavorite();
                }
            });
        }

        ImageView imgBackdropPath = (ImageView) view.findViewById(R.id.item_poster_patch);
           String imageUr1 = String.format("%s%s%s", "https://image.tmdb.org/t/p/", // base
                   "w1280", // size
                   result.getBackdropPath());

          Picasso.with(getActivity())
                   .load(imageUr1)
                   .into(imgBackdropPath);

        originalTitle     = (TextView) view.findViewById(R.id.txt_original_title);
        originalTitle.setText("Original Title: " + result.getOriginalTitle());

        releaseDate       = (TextView) view.findViewById(R.id.txt_release_date);
        releaseDate.setText("Release Date: " + result.getReleaseDate());

        original_language = (TextView) view.findViewById(R.id.txt_original_language);
        original_language.setText("Original Language: " + result.getOriginalLanguage());

        overViewer        = (TextView) view.findViewById(R.id.txt_over_viewer);
        overViewer.setText("Overview: " + result.getOverview());

        tmDbDao = TmDbDao.getInstance(getActivity().getApplication().getApplicationContext());
        result = tmDbDao.getResult(result);
        isFavorite = result == null ? false : true;
        changeFloatingButton();

        return view;
    }

    private void changeFloatingButton() {
        int resource = isFavorite ? R.drawable.ic_favorite_white_24dp : R.drawable.ic_favorite_border_white_24dp;
        floatingActionButton.setImageResource(resource);
    }

    public void seveOrRemoveFavorite(){
        if(isFavorite){
            tmDbDao.deleteTmDb(result);
            isFavorite = false;
        }else{
            tmDbDao.insertTmDb(result);
            isFavorite = true;
        }
        changeFloatingButton();
        EventBus.getDefault().post(new DatabaseEvent());
    }

}
