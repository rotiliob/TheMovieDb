package br.com.geekemovies.themoviedb.dataBase;

import android.provider.BaseColumns;

/**
 * Created by andre on 08/12/2016.
 */
public interface DatabaseConstants extends BaseColumns {
     String TMDB = "TmDb";
     String POSTERPATCH = "posterPatch";
     String ORIGINALTITLE = "originalTitle";
     String RELEASEDATE = "releaseDate";
     String ORIGINALLANGUAGE ="originaLanguage";
     String OVERVIEWER ="overViewer";

         StringBuilder CREATE_TMDB =
            new StringBuilder("create table" + TMDB)
                    .append("(" + _ID        + "integer primary key autoincrement, ")
                    .append(POSTERPATCH      + "text,")
                    .append(ORIGINALTITLE    + "text,")
                    .append(RELEASEDATE      + "text,")
                    .append(ORIGINALLANGUAGE + "text,")
                    .append(OVERVIEWER       + "text,");

}
