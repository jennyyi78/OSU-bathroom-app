package com.example.osu_bathroom_app.main;

import android.app.Application;

public class GlobalClass extends Application {

    private long userId;
    private long currentPage;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }
}
