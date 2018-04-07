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

import android.net.Uri;
import android.provider.BaseColumns;

import com.aiassoft.popularmovies.Const;

public class PopularMoviesContract {

    /**
     * We never need to create an instance of the contract class
     * because the contract is simply a class filled,
     * with DB related constants that are all static.
     */
        private PopularMoviesContract() {}

    /**
     * Inner class that defines the FavoriteMovies table contents
     */
    public static class FavoriteMoviesEntry implements BaseColumns {
        /** This final content URI will include the scheme, the authority,
         *  and our FAVORITE_MOVIES path.
         */
        public static final Uri CONTENT_URI = Const.BASE_CONTENT_URI.buildUpon()
                .appendPath(Const.PATH_FAVORITE_MOVIES).build();

        public static final String TABLE_NAME = "favoriteMovies";

        public static final String COLUMN_NAME_THEMOVIEDB_ID = "theMovieDBId";
        public static final String COLUMN_NAME_MOVIE_TITLE = "movieTitle";
        public static final String COLUMN_NAME_POSTERPATH = "posterPath";
    }

}
