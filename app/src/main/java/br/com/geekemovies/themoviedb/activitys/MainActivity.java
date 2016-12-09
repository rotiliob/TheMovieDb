package br.com.geekemovies.themoviedb.activitys;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import org.greenrobot.eventbus.EventBus;
import br.com.geekemovies.themoviedb.R;
import br.com.geekemovies.themoviedb.fragments.DetailTmDbFragment;
import br.com.geekemovies.themoviedb.fragments.FavoritesTmDbFragment;
import br.com.geekemovies.themoviedb.fragments.TmDbListFragment;
import br.com.geekemovies.themoviedb.interfaces.OnTmDbClick;
import br.com.geekemovies.themoviedb.model.Result;

public class MainActivity extends AppCompatActivity implements OnTmDbClick {

    Toolbar toolbar;
    TmDbListFragment tmDbListFragment;
    FavoritesTmDbFragment favoritesTmDbFragment;
    ViewPager mViewPager;
    SelectorPageAdapter selectorPageAdapter;
    TabLayout tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buildViewPager();

    }

    private void buildViewPager(){
        mViewPager = (ViewPager)findViewById(R.id.container);
        selectorPageAdapter = new SelectorPageAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(selectorPageAdapter);

        tab = (TabLayout) findViewById(R.id.tabs);
        tab.setupWithViewPager(mViewPager);
    }


    @Override
    public void onTmDbClick(Result result) {
        if (getResources().getBoolean(R.bool.phone)) {
            Intent it = new Intent(this, DetailTmDbAct.class);
            it.putExtra("result", result);
            startActivity(it);
        } else {

            DetailTmDbFragment detailTmDbFragment = DetailTmDbFragment.newInstance(result);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_tmdb_detail, detailTmDbFragment, "datail")
                    .commit();
        }
    }

    public class SelectorPageAdapter extends FragmentPagerAdapter{
        public SelectorPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (tmDbListFragment == null) {
                        tmDbListFragment = new TmDbListFragment();
                    }
                    return tmDbListFragment;
                case 1:
                default:
                    if (favoritesTmDbFragment == null) {
                        favoritesTmDbFragment = new FavoritesTmDbFragment();
                    }
                    return favoritesTmDbFragment;
            }
        }

        @Override
        public int getCount(){return 2;}

        @Override
        public CharSequence getPageTitle(int position){
            switch (position){
                case 0:
                    return "lista";
                case 1:
                    default:
                        return "Favoritos";
            }
        }
    }
}
