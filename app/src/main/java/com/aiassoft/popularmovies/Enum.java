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

package com.aiassoft.popularmovies;

/*
 * Defining a class of enums to use them from any where
 *
 */
public class Enum {

    /**
     * The access type, to determine how to read the data
     * over the internet or from local db
     */
    public enum sortByAccessType {
        BY_THE_MOVIE_DB,
        BY_LOCAL_DB;
    }

    /**
     * The Sort By Enums,
     * with the method getUrl to get the right web url based on the selected Sort By type
     */
    public enum theMovieDbSortBy {
        MOST_POPULAR,
        HIGHEST_RATED,
        FAVORITE;

        /**
         * Get the right web url based on the selected Sort By type
         *
         * @return The web url based on the selected Sort By type
         */
        public String getUrl() {
            switch (this) {
                case MOST_POPULAR:
                    return Const.THEMOVIEDB_BASE_URL + Const.THEMOVIEDB_POPULAR_MOVIES;
                case HIGHEST_RATED:
                    return Const.THEMOVIEDB_BASE_URL + Const.THEMOVIEDB_TOP_RATED_MOVIES;
                case FAVORITE:
                    throw new AssertionError(this + MyApp.getContext().getString(R.string.doesnt_returns_a_url));
                default:
                    throw new AssertionError(MyApp.getContext().getString(R.string.unknown_sort_by) + this);
            }
        }

        /**
         * Get the right access type based on the selected Sort By type
         *
         * @return the access type, to determine how to read the data
         */
        public sortByAccessType getAccessType() {
            switch (this) {
                case MOST_POPULAR:
                case HIGHEST_RATED:
                    return sortByAccessType.BY_THE_MOVIE_DB;
                case FAVORITE:
                    return sortByAccessType.BY_LOCAL_DB;
                default:
                    throw new AssertionError(MyApp.getContext().getString(R.string.unknown_access_type) + this);
            }
        }
    }
}
