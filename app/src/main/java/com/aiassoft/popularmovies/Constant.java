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

import android.net.Uri;

/*
 * Defining a class of constants to use them from any where
 *
 */
public class Constant {
    /**
     * We never need to create an instance of the Constant class
     * because the Constant is simply a class filled,
     * with App related constants that are all static.
     */
    private Constant() {}

    /**
     * themoviedb.org API KEY
     */
    public static final String THEMOVIEDB_API_KEY = BuildConfig.THEMOVIEDB_API_KEY;
    /**
     * themoviedb.org URI definitions
     */
    public static final String THEMOVIEDB_BASE_URL = "http://api.themoviedb.org/3";
    public static final String THEMOVIEDB_MOVIE = "/movie";
    public static final String THEMOVIEDB_VIDEOS = "/videos";
    public static final String THEMOVIEDB_REVIEWS = "/reviews";
    public static final String THEMOVIEDB_POPULAR_MOVIES = "/movie/popular";
    public static final String THEMOVIEDB_TOP_RATED_MOVIES = "/movie/top_rated";
    public static final String THEMOVIEDB_POSTER_IMAGE_BASE_URL = "http://image.tmdb.org/t/p";
    public static final String THEMOVIEDB_POSTER_IMAGE_THUMBNAIL_SIZE = "/w185";
    public static final String THEMOVIEDB_PARAM_API_KEY = "api_key";


    /**
     * youtube definitions
     */
    public static final String YOUTUBE_APP_URI = "vnd.youtube:";
    public static final String YOUTUBE_WATCH_URL = "https://www.youtube.com/watch";
    public static final String YOUTUBE_PARAM_WATCH_VIDEO = "v";

    /**
     * This ID will be used to identify the Loader responsible for loading our movies list.
     */
    public static final int MOVIES_LOADER_ID = 0;

    /**
     * Contract's & Content Provider's definitions
     */
    // The authority, which is how the code knows which Content Provider to access
    // The authority is defined in the Android Manifest.
    public static final String AUTHORITY = "com.aiassoft.popularmovies";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "FavoriteMovies" directory, that will be appended
    // to the base content URI
    public static final String PATH_FAVORITE_MOVIES = "FavoriteMovies";

}
