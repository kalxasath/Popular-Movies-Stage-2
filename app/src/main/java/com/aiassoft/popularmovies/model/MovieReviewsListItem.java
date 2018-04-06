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
 * The Movie Reviews List Item Object, holds all the necessary information of a list item
 */
public class MovieReviewsListItem {
    private static final String LOG_TAG = MyApp.APP_TAG + MovieReviewsListItem.class.getSimpleName();

    private String author;
    private String content;

    /**
     * No args constructor for use in serialization
     */
    public MovieReviewsListItem() {
    }

    /**
     * Constructor to initialize all the class fields from the parameters
     * @param author  The review author
     * @param content The review content
     */
    public MovieReviewsListItem(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
