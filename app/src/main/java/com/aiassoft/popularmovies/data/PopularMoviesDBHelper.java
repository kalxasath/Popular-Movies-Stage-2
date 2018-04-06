/**
 * Copyright (C) 2018 by George Vrynios
 * This project was made under the supervision of Udacity
 * in the Android Developer Nanodegree Program
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aiassoft.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aiassoft.popularmovies.data.PopularMoviesContract.FavoriteMoviesEntry;

/**
 * SQLite DB Helper
 * to take care of creating the database for the first time
 * and upgrading it when the schema changes.
 */
public class PopularMoviesDBHelper extends SQLiteOpenHelper {

    /**
     * The database name
     */
    private static final String DATABASE_NAME = "popularmovies.db";

    /**
     * The database version number
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * The class constructor
     * @param context the context to pass through to the parent constructor
     */
    public PopularMoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITEMOVIES_TABLE = "CREATE TABLE " +
                FavoriteMoviesEntry.TABLE_NAME + " (" +
                FavoriteMoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoriteMoviesEntry.COLUMN_NAME_THEMOVIEDB_ID + " INTEGER NOT NULL, " +
                FavoriteMoviesEntry.COLUMN_NAME_MOVIE_TITLE + "  TEXT NOT NULL, " +
                FavoriteMoviesEntry.COLUMN_NAME_POSTERPATH + "  TEXT NOT NULL " +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITEMOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int prevVersion, int newVersion) {
        /**
         * At the moment we have only one version of our database
         * so we don't need to implement any version change here
         */
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteMoviesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
