package com.chifanhero.api.services.google.client.request;

/**
 * Created by shiyan on 5/2/17.
 */
public enum RankBy {

    /**
     * This option sorts results based on their importance. Ranking will favor prominent places within the specified area. Prominence can be affected by a place's ranking in Google's index, global popularity, and other factors.
     */
    prominence,

    /**
     * This option biases search results in ascending order by their distance from the specified location. When distance is specified, one or more of keyword, name, or type is required.
     */
    distance
}
