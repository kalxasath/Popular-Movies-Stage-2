/*
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

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.aiassoft.popularmovies.data.PopularMoviesContract.FavoriteMoviesEntry;
import com.aiassoft.popularmovies.model.Movie;
import com.aiassoft.popularmovies.model.MovieReviewsListItem;
import com.aiassoft.popularmovies.model.MovieVideosListItem;
import com.aiassoft.popularmovies.utilities.JsonUtils;
import com.aiassoft.popularmovies.utilities.NetworkUtils;
import com.aiassoft.popularmovies.utilities.YoutubeUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gvryn on 04/03/18.
 * Displays the details of a movie
 */
public class DetailActivity extends AppCompatActivity
        implements MovieVideosListAdapter.MovieVideosAdapterOnClickHandler {
    //LoaderCallbacks<List<MovieVideosListItem>>

    private static final String LOG_TAG = MyApp.APP_TAG + DetailActivity.class.getSimpleName();

    /**
     * Identifies the incoming parameter of the movie id
     */
    public static final String EXTRA_MOVIE_ID = "movie_id";

    /**
     * If there is not a movie id, this id will as the default one
     */
    private static final int DEFAULT_MOVIE_ID = -1;

    private static Context mContext = null;
    private static Movie mMovie = null;
    private static int mMovieId = DEFAULT_MOVIE_ID;
    private static ArrayList<MovieVideosListItem> mMovieVideosListItems;
    private static ArrayList<MovieReviewsListItem> mMovieReviewsListItems;

    /**
     * The Videos & Reviews Adapters
     */
    private MovieVideosListAdapter mMovieVideosListAdapter;
    private MovieReviewsListAdapter mMovieReviewsListAdapter;

    /**
     * The views in the xml file
     */
    @BindView(R.id.sv_movie_details)
    ScrollView mMovieDetails;
    @BindView(R.id.iv_movie_poster)
    ImageView mMoviePoster;
    @BindView(R.id.tv_original_title)
    TextView mOriginalTitle;
    @BindView(R.id.tv_overview)
    TextView mOverview;
    @BindView(R.id.tv_release_date)
    TextView mReleaseDate;
    @BindView(R.id.tv_vote_average)
    TextView mVoteAverage;
    @BindView(R.id.tv_runtime)
    TextView mRuntime;
    @BindView(R.id.btn_favorite)
    Button mFavoriteButton;

    @BindView(R.id.tv_trailers_title)
    TextView mVideosTitle;
    @BindView(R.id.rv_videos)
    RecyclerView mRecyclerViewVideos;

    @BindView(R.id.tv_reviews_title)
    TextView mReviewsTitle;
    @BindView(R.id.rv_reviews)
    RecyclerView mRecyclerViewReviews;

    /**
     * The Error Message Block,
     * is used to display errors and will be hidden if there are no error
     */
    @BindView(R.id.ll_error_message)
    LinearLayout mErrorMessageBlock;

    /**
     * The view holding the error message
     */
    @BindView(R.id.tv_error_message_text)
    TextView mErrorMessageText;

    /**
     * The ProgressBar that will indicate to the user that we are loading data.
     * It will be hidden when no data is loading.
     */
    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingIndicator;


    /**
     * Creates the detail activity
     *
     * @param savedInstanceState The saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        /** should be called from another activity. if not, show error toast and return */
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        /** Intent parameter should be a valid movie id. if not, show error toast and return */
        mMovieId = intent.getIntExtra(EXTRA_MOVIE_ID, DEFAULT_MOVIE_ID);
        if (mMovieId == DEFAULT_MOVIE_ID) {
            // EXTRA_MOVIE_ID not found in intent's parameter
            closeOnError();
        }


        /**
         *  Initialize Videos Section
         */
        LinearLayoutManager linearLayoutVideoManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerViewVideos.setLayoutManager(linearLayoutVideoManager);
        mRecyclerViewVideos.setHasFixedSize(true);

        /**
         * The mMovieVideosListAdapter is responsible for linking our movie's videos data with the Views that
         * will end up displaying our videos' data.
         */
        mMovieVideosListAdapter = new MovieVideosListAdapter(this);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerViewVideos.setAdapter(mMovieVideosListAdapter);

        /**
         *  Initialize Reviews Section
         */
        LinearLayoutManager linearLayoutReviewsManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerViewReviews.setLayoutManager(linearLayoutReviewsManager);
        mRecyclerViewReviews.setHasFixedSize(true);

        /**
         * The mMovieReviewsListAdapter is responsible for linking our movie's reviews data with the Views that
         * will end up displaying our reviews' data.
         */
        mMovieReviewsListAdapter = new MovieReviewsListAdapter();

        /** Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerViewReviews.setAdapter(mMovieReviewsListAdapter);

        /** We will check if we are connected to the internet */
        if (!NetworkUtils.isOnline()) {
            /** We are not connected, show the Error Block
             *  with the propriety error message
             */
            showErrorMessage(R.string.error_check_your_network_connectivity);
        } else {
            /** Otherwise fetch movies' data from the internet */
            fetchMoviesDetails();
        }

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void fetchMoviesDetails() {
        new TheMovieDbQueryTask().execute(NetworkUtils.buildMovieUrl(mMovieId),
                NetworkUtils.buildAddonsUrl(mMovieId, Const.THEMOVIEDB_VIDEOS),
                NetworkUtils.buildAddonsUrl(mMovieId, Const.THEMOVIEDB_REVIEWS));
    }

    @Override
    protected void onStop() {
        invalidateActivity();
        super.onStop();
    }

    private void invalidateActivity() {
        mContext = null;
        mMovie = null;
        mMovieId = DEFAULT_MOVIE_ID;
    }

    /**
     * This method will take a movie object as input
     * and use that object's data to populate the UI.
     */
    private void populateUI() {
        Picasso.with(mContext)
                .load(NetworkUtils.buildPosterUrl(mMovie.getPosterPath()))
                .into(mMoviePoster);

        mOriginalTitle.setText(mMovie.getOriginalTitle());
        mOverview.setText(mMovie.getOverview());
        mReleaseDate.setText(mMovie.getReleaseDate());
        mVoteAverage.setText(mMovie.getVoteAverage() + MyApp.mMaxRating);
        mRuntime.setText(mMovie.getRunTime() + MyApp.mMinutes);

        mFavoriteButton.setVisibility(View.VISIBLE);
        if (mMovie.getIsFavorite()) {
            mFavoriteButton.setText(R.string.remove_from_favorites);
        }

        /* Update the Videos adapters data with the new one */
        if (mMovieVideosListItems.size() != 0) {
            mMovieVideosListAdapter.setMovieVideosData(mMovieVideosListItems);
        } else {
            mVideosTitle.setVisibility(View.INVISIBLE);
            mRecyclerViewVideos.setVisibility(View.INVISIBLE);
        }

        /** Update the Reviews adapters data with the new one */
        if (mMovieReviewsListItems.size() != 0) {
            mMovieReviewsListAdapter.setMovieReviewsData(mMovieReviewsListItems);
        } else {
            mReviewsTitle.setVisibility(View.INVISIBLE);
            mRecyclerViewReviews.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.btn_favorite)
    public void onViewClicked() {
    }

    public class TheMovieDbQueryTask extends AsyncTask<URL, Void, ArrayList<String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<String> doInBackground(URL... urls) {
            ArrayList<String> moviesData = new ArrayList<>();

            try {
                moviesData.add(NetworkUtils.getResponseFromHttpUrl(urls[0]));
                moviesData.add(NetworkUtils.getResponseFromHttpUrl(urls[1]));
                moviesData.add(NetworkUtils.getResponseFromHttpUrl(urls[2]));
                moviesData.add(movieIsFavorite());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return moviesData;
        } // doInBackground

        private String movieIsFavorite() {
            /**
             * We need to access the movie by the member variable mMovieId,
             * since the mMovie class is not initialized yet
             */
            String stringId = "" + mMovieId;
            Uri uri = FavoriteMoviesEntry.CONTENT_URI;
            uri = uri.buildUpon().appendPath(stringId).build();
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            if (cursor == null) {
                return "0";
            } else {
                String retStatus = cursor.getCount() == 0 ? "0" : "1";
                cursor.close();

                return (retStatus);
            }
        }

        @Override
        protected void onPostExecute(ArrayList<String> s) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);


            if (s != null) {
                //Log.d(LOG_TAG, s);

                /** onStop() invalidates mContext & mMovie */
                if (mContext != null) {
                    mMovie = JsonUtils.parseMoviesJson(s.get(0));
                    mMovie.setId(mMovieId);
                    if (s.get(3) == "1") {
                        mMovie.setIsFavorite(true);
                    } else {
                        mMovie.setIsFavorite(false);
                    }
                    mMovieVideosListItems = JsonUtils.parseMovieVideosListJson(s.get(1));
                    mMovieReviewsListItems = JsonUtils.parseMovieReviewsListJson(s.get(2));
                    Log.d(LOG_TAG, s.get(0));
                    Log.d(LOG_TAG, s.get(1));
                    Log.d(LOG_TAG, s.get(2));

                    if (mMovie != null) {
                        populateUI();
                    }
                }
            } else {
                showErrorMessage(R.string.unexpected_fetch_error);
            }
        } // onPostExecute

    } // class TheMovieDbQueryTask


    /**
     * This method will make the View for the movie's details visible and
     * hides the error message block.
     */
    private void showMoviesDetails() {
        /* First, make sure the error block is invisible */
        mErrorMessageBlock.setVisibility(View.INVISIBLE);
        /* Then, make sure the movie details are visible */
        mMovieDetails.setVisibility(View.VISIBLE);
    } // showMoviesDetails

    /**
     * This method will make the error message block visible,
     * populate the error message with the corresponding error message,
     * and hides the movie details.
     *
     * @param errorId The error message string id
     */
    private void showErrorMessage(int errorId) {
        /* First, hide the currently visible movie details */
        mMovieDetails.setVisibility(View.INVISIBLE);
        /* Then, show the error block */
        mErrorMessageBlock.setVisibility(View.VISIBLE);
        /* Show the corresponding error message */
        mErrorMessageText.setText(getString(errorId));
    } // showErrorMessage

    /**
     * Called when a tap occurs in the refresh button
     *
     * @param view The view which reacted to the click
     */
    public void onRefreshButtonClick(View view) {
        /** Again check if we are connected to the internet */
        if (NetworkUtils.isOnline()) {
            /** If the network connectivity is restored
             *  show the Movie Details to hide the error block, and
             *  fetch movies' data from the internet
             */
            showMoviesDetails();
            fetchMoviesDetails();
        }
    } // onRefreshButtonClick

    /**
     * This method is for responding to clicks from our Videos list.
     *
     * @param movieArrayItem the array item from the selected video
     */
    @Override
    public void onVideoClick(int movieArrayItem) {
        Toast.makeText(this, mMovieVideosListItems.get(movieArrayItem).getVideoTitle(), Toast.LENGTH_SHORT).show();
        YoutubeUtils.watchYoutubeVideo(this, mMovieVideosListItems.get(movieArrayItem).getKey());
    }

    /**
     * SECTION to handle movie's favorite actions & methods
     */

    /**
     * This method is for responding to clicks from the Favorites Button
     *
     * @param view The view which reacted to the click
     */
    public void onFavoritesButtonClick(View view) {
        if (mMovie.getIsFavorite()) {
            removeFromFavorite();
        } else {
            addToFavorite();
        }
    }


    /**
     * This method insert the desired movie's data in the FAVORITE_MOVIES directory
     * after that the movie is marked as favorite
     */
    private void addToFavorite() {
        /** We'll create a new ContentValues object to place data into. */
        ContentValues contentValues = new ContentValues();

        /** Put the movie's id and the title into the ContentValues */
        contentValues.put(FavoriteMoviesEntry.COLUMN_NAME_THEMOVIEDB_ID, mMovie.getId());

        contentValues.put(FavoriteMoviesEntry.COLUMN_NAME_MOVIE_TITLE, mMovie.getOriginalTitle());
        contentValues.put(FavoriteMoviesEntry.COLUMN_NAME_POSTERPATH, mMovie.getPosterPath());

        /**
         * Insert new Movie's data via a ContentResolver
         * Then we need to insert these values into our database with
         * a call to a content resolver
         */
        Uri uri = getContentResolver().insert(FavoriteMoviesEntry.CONTENT_URI, contentValues);

        if (uri != null) {
            mFavoriteButton.setText(R.string.remove_from_favorites);
            mMovie.setIsFavorite(true);
        } else {
            Toast.makeText(getBaseContext(), "Couldn't insert Favorite", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * To un-favorite a movie, we have just to delete the movie's data from the
     * FAVORITE_MOVIES directory, and un-mark it as favorite
     */
    private void removeFromFavorite() {
        /**
         * We will remove a movie from our favorite list by removing it from the
         * FAVORITE_MOVIES directory, so we have to pass in the content providers path
         * the theMovieDBId
         */
        String stringId = "" + mMovie.getId();
        Uri uri = FavoriteMoviesEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(stringId).build();
        int deletedRecords = getContentResolver().delete(uri, null, null);

        if (deletedRecords > 0) {
            mFavoriteButton.setText(R.string.add_to_favorites);
            mMovie.setIsFavorite(false);
        }
    }

}
