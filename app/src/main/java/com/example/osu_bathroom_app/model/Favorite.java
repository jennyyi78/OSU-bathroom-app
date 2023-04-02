package com.example.osu_bathroom_app.model;

public class Favorite {

    long bathroomId;
    String user;

    public Favorite(long bathroomId, String user)
    {
        this.bathroomId=bathroomId;
        this.user=user;
    }
    public long getBathroomId()
    {
        return bathroomId;
    }
    public String getUser()
    {
        return user;
    }
}
