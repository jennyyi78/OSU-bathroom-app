package com.example.osu_bathroom_app.model;

public class Review
{
    float rating;
    String review;
    long id;

    public Review(long id,float rating, String review)
    {
        this.id=id;
        this.rating=rating;
        this.review=review;
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
       this.rating=newRating;
   }
   public String getReview()
   {
       return review;
   }
   public void setReview(String newReview)
   {
       this.review=newReview;
   }
}
