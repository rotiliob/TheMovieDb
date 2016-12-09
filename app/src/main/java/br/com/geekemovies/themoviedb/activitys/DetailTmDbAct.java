package br.com.geekemovies.themoviedb.activitys;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.geekemovies.themoviedb.R;
import br.com.geekemovies.themoviedb.fragments.DetailTmDbFragment;
import br.com.geekemovies.themoviedb.model.Result;

public class DetailTmDbAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tmdb);

        Result result = (Result) getIntent().getSerializableExtra("result");

        DetailTmDbFragment detailTmDbFragment = DetailTmDbFragment.newInstance(result);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_tmdb_detail,detailTmDbFragment,"detail")
                .commit();


    }
}
