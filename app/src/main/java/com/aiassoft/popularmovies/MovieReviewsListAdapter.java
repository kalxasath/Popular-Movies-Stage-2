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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aiassoft.popularmovies.model.MovieReviewsListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link MoviesListAdapter} exposes a list of movie reviews to a
 * {@link android.support.v7.widget.RecyclerView}
 */
public class MovieReviewsListAdapter extends RecyclerView.Adapter<MovieReviewsListAdapter.MoviesReviewsAdapterViewHolder> {

    private static final String LOG_TAG = MyApp.APP_TAG + MovieReviewsListAdapter.class.getSimpleName();

    /* This array holds a list of movie reviews objects */
    private ArrayList<MovieReviewsListItem> mMovieReviewsData = new ArrayList<>();

    /**
     * Creates a MovieReviewsListAdapter
     *
     */
    public MovieReviewsListAdapter() {
    }

    /**
     * Cache of the children views for a movie review list item
     */
    public class MoviesReviewsAdapterViewHolder extends RecyclerView.ViewHolder {

        /* The reviews views */
        private final TextView mReviewAuthor;
        private final TextView mReviewContent;

        public MoviesReviewsAdapterViewHolder(View view) {
            super(view);
            mReviewAuthor = (TextView) view.findViewById(R.id.tv_review_author);
            mReviewContent = (TextView) view.findViewById(R.id.tv_review_content);
        }

    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't) you
     *                  can use this viewType integer to provide a different layout.
     * @return A new MovieVideosAdapterViewHolder that holds the View for each list item
     */
    @Override
    public MoviesReviewsAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_review_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MoviesReviewsAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the movie
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param moviesReviewsAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                contents of the item at the given position in the data set.
     * @param position                The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MoviesReviewsAdapterViewHolder moviesReviewsAdapterViewHolder, int position) {
        String author = mMovieReviewsData.get(position).getAuthor();
        String content = mMovieReviewsData.get(position).getContent();

        moviesReviewsAdapterViewHolder.mReviewAuthor.setText(author);
        moviesReviewsAdapterViewHolder.mReviewContent.setText(content);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our movies list
     */
    @Override
    public int getItemCount() {
        if (null == mMovieReviewsData) return 0;
        return mMovieReviewsData.size();
    }

    /**
     * This method is used to set the movie on a MoviesAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new MoviesAdapter to display it.
     *
     * @param moviesReviewsData The new movie reviews data to be displayed.
     */
    public void setMovieReviewsData(List<MovieReviewsListItem> moviesReviewsData) {
        if (moviesReviewsData == null) return;
        mMovieReviewsData.addAll(moviesReviewsData);
        notifyDataSetChanged();
    }

    /**
     * This method is used when we are resetting data
     */
    public void invalidateReviewsData() {
        mMovieReviewsData = new ArrayList<MovieReviewsListItem>();
        notifyDataSetChanged();
    }

}
