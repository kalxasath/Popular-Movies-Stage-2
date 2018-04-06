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

package com.aiassoft.popularmovies.model;

import com.aiassoft.popularmovies.MyApp;

/**
 * The Movie Videos List Item Object, holds all the necessary information of a list item
 */
public class MovieVideosListItem {
    private static final String LOG_TAG = MyApp.APP_TAG + MovieVideosListItem.class.getSimpleName();

    private String key;
    private String videoTitle;
    private String mediaSite;
    private String videoType;

    /**
     * No args constructor for use in serialization
     */
    public MovieVideosListItem() {
    }

    /**
     * Constructor to initialize all the class fields from the parameters
     * @param key        The video key
     * @param videoTitle The video title
     * @param mediaSite  The media site hosting the video
     * @param videoType  The type of video
     */
    public MovieVideosListItem(String key, String videoTitle, String mediaSite, String videoType) {
        this.key = key;
        this.videoTitle = videoTitle;
        this.mediaSite = mediaSite;
        this.videoType = videoType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getMediaSite() {
        return mediaSite;
    }

    public void setMediaSite(String mediaSite) {
        this.mediaSite = mediaSite;
    }

    public String getVideoType() {
        return videoType;
    }

    public void setVideoType(String videoType) {
        this.videoType = videoType;
    }

}
