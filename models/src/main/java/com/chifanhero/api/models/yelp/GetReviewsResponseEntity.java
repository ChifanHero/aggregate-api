package com.chifanhero.api.models.yelp;

import java.util.List;

/**
 * Created by shiyan on 4/27/17.
 */
public class GetReviewsResponseEntity {

    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
