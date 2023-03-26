package com.example.osu_bathroom_app.model;

public class Bathroom
{
    long id;
    String name;
    String address;

    String info;

    float avgRating;

    public Bathroom(long id, String name, String address, float rating, String info)
    {
        this.id=id;
        this.name = name;
        this.address = address;
        this.avgRating=rating;
        this.info=info;
    }

    public long getId(){return this.id;}

    public String getInfo()
    {
        return this.info;
    }

    public float getAvgRating(){return this.avgRating;}
    public void setAvgRating(float rating)
    {
        this.avgRating=rating;
    }
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
}
