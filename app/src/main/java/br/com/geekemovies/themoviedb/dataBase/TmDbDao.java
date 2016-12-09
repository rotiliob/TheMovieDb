package br.com.geekemovies.themoviedb.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.geekemovies.themoviedb.R;
import br.com.geekemovies.themoviedb.model.Result;

/**
 * Created by andre on 08/12/2016.
 */

public class TmDbDao {
    private static TmDbDao instance;
    private Context context;

    private TmDbDao(Context context) {
        this.context = context;
    }

    public static synchronized TmDbDao getInstance(Context context) {
        if (instance == null) {
            instance = new TmDbDao(context);
        }
        return instance;
    }

    public long insertTmDb(Result resultTmDb) {
        long result = -1;
        if (resultTmDb != null) {
            MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
            SQLiteDatabase db = mySqlOpenHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseConstants.POSTERPATCH, resultTmDb.getBackdropPath());
            contentValues.put(DatabaseConstants.ORIGINALTITLE, resultTmDb.getOriginalTitle());
            contentValues.put(DatabaseConstants.RELEASEDATE, resultTmDb.getReleaseDate());
            contentValues.put(DatabaseConstants.ORIGINALLANGUAGE, resultTmDb.getOriginalLanguage());
            contentValues.put(DatabaseConstants.OVERVIEWER, resultTmDb.getOverview());
            try {
                result = db.insert(DatabaseConstants.TMDB, null, contentValues);
            } finally {
                db.close();
            }

        }
        return result;
    }

    public long deleteTmDb(Result resultTmDb) {
        long result = -1;

        if (resultTmDb != null) {
            MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
            SQLiteDatabase db = mySqlOpenHelper.getWritableDatabase();
            try {
                result = db.delete(DatabaseConstants.TMDB,DatabaseConstants._ID + " = ? ",new String[]{String.valueOf(resultTmDb.getId())} );
            } finally {
                db.close();
            }
        }
        return result;
    }

    public Result getResult(Result resultTmDb){
        Result result = null;

        if (resultTmDb != null) {
            MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
            SQLiteDatabase db = mySqlOpenHelper.getWritableDatabase();

            Cursor cursor = db.rawQuery("select * from " + DatabaseConstants.TMDB + " where " + DatabaseConstants._ID + " = ?",
                    new String[]{String.valueOf(resultTmDb.getId())});
            if (cursor.moveToFirst()) {
                result = getTmDbFromCursor(cursor);
            }
            }
        return result;
    }

    public List<Result> getFavoriteTmDb(){
        ArrayList resultList = new ArrayList<>();

        MySqlOpenHelper mySqlOpenHelper = new MySqlOpenHelper(context);
        SQLiteDatabase db = mySqlOpenHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from "+ DatabaseConstants.TMDB, null);

        while (cursor.moveToNext()){
            resultList.add(getTmDbFromCursor(cursor));
        }
        return resultList;
    }

    public Result getTmDbFromCursor(Cursor cursor){
        Result result = new Result();
        if (cursor != null){
            result.setBackdropPath(cursor.getString(cursor.getColumnIndex(DatabaseConstants.POSTERPATCH)));
            result.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(DatabaseConstants._ID))));
            result.setOriginalTitle(cursor.getString(cursor.getColumnIndex(DatabaseConstants.ORIGINALTITLE)));
            result.setReleaseDate(cursor.getString(cursor.getColumnIndex(DatabaseConstants.RELEASEDATE)));
            result.setOriginalLanguage(cursor.getString(cursor.getColumnIndex(DatabaseConstants.ORIGINALLANGUAGE)));
            result.setOverview(cursor.getString(cursor.getColumnIndex(DatabaseConstants.OVERVIEWER)));
        }

        return  result;
    }
}
