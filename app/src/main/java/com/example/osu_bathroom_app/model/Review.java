package com.example.osu_bathroom_app.model;

public class Review
{
    float rating;
    String review;
    long bathroomId;
    long userId;
    long id;

    public Review(long id, long bid, float rating, String review, long userId)
    {
        this.id = id;
        this.bathroomId = bid;
        this.rating = rating;
        this.review = review;
        this.userId = userId;
    }

    public long getId()
    {
        return id;
    }

    public float getRating()
    {
        return this.rating;
    }

    public void setRating(float newRating)
    {
        this.rating = newRating;
    }

    public String getReview()
    {
        return review;
    }

    public void setReview(String newReview)
    {
        this.review = newReview;
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(long userId)
    {
        this.userId = userId;
    }

    public long getBathroomId()
    {
        return bathroomId;
    }

    public void setBathroomId(long bathroomId)
    {
        this.bathroomId = bathroomId;
    }
}
