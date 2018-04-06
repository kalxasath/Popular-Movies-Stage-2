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
import android.widget.ImageView;
import android.widget.TextView;

import com.aiassoft.popularmovies.model.MovieVideosListItem;
import com.aiassoft.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link MoviesListAdapter} exposes a list of movie videoss to a
 * {@link android.support.v7.widget.RecyclerView}
 */
public class MovieVideosListAdapter extends RecyclerView.Adapter<MovieVideosListAdapter.MovieVideosAdapterViewHolder> {

    private static final String LOG_TAG = MyApp.APP_TAG + MovieVideosListAdapter.class.getSimpleName();

    /* This array holds a list of movie videos objects */
    private ArrayList<MovieVideosListItem> mMovieVideosData = new ArrayList<MovieVideosListItem>();

    /**
     * Defining an on-click handler to make it easy for an Activity
     * to interface with the RecyclerView
     */
    private final MovieVideosAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives OnClick messages
     */
    public interface MovieVideosAdapterOnClickHandler {
        void onVideoClick(int movieArrayItem);
    }

    /**
     * Creates a MovieVideosListAdapter
     *
     * @param clickHandler The on-click handler for this adapter. This single handler
     *                     is called when an item is clicked
     */
    public MovieVideosListAdapter(MovieVideosAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    /**
     * Cache of the children views for a movie video list item
     */
    public class MovieVideosAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        /* This ImageView is used to display the Movie's Poster */
        private final TextView mVideoTitle;

        public MovieVideosAdapterViewHolder(View view) {
            super(view);
            mVideoTitle = view.findViewById(R.id.tv_video_title);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            //int selectedMovie = mMovieVideosData.get(adapterPosition).getId();
            //mClickHandler.onVideoClick(selectedMovie);
            mClickHandler.onVideoClick(adapterPosition);
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
    public MovieVideosAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_video_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieVideosAdapterViewHolder(view);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the movie
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param movieVideosAdapterViewHolder The ViewHolder which should be updated to represent the
     *                                contents of the item at the given position in the data set.
     * @param position                The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MovieVideosAdapterViewHolder movieVideosAdapterViewHolder, int position) {
        String s = mMovieVideosData.get(position).getVideoTitle();

        movieVideosAdapterViewHolder.mVideoTitle.setText(s);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our movies list
     */
    @Override
    public int getItemCount() {
        if (null == mMovieVideosData) return 0;
        return mMovieVideosData.size();
    }

    /**
     * This method is used to set the video on a VideosAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new VideosAdapter to display it.
     *
     * @param movieVideosData The new video's data to be displayed.
     */
    public void setMovieVideosData(List<MovieVideosListItem> movieVideosData) {
        if (movieVideosData == null) return;
        mMovieVideosData.addAll(movieVideosData);
        notifyDataSetChanged();
    }

    /**
     * This method is used when we are resetting data
     */
    public void invalidateVideosData() {
        mMovieVideosData = new ArrayList<MovieVideosListItem>();
        notifyDataSetChanged();
    }

}
