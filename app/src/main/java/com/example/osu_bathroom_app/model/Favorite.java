package com.example.osu_bathroom_app.model;

public class Favorite
{

    long bathroomId;
    long userId;

    public Favorite(long bathroomId, long userId)
    {
        this.bathroomId = bathroomId;
        this.userId = userId;
    }

    public long getBathroomId()
    {
        return bathroomId;
    }

    public long getUserId()
    {
        return userId;
    }
}
