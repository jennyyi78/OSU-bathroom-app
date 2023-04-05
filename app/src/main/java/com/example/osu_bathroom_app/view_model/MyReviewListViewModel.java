package com.example.osu_bathroom_app.view_model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.osu_bathroom_app.model.Review;
import com.example.osu_bathroom_app.repository.BathroomRepository;

import java.util.List;

public class MyReviewListViewModel extends ViewModel
{


    private MutableLiveData<List<Review>> mReviews;

    private BathroomRepository mRepo;


    public void init(long id)
    {
        if (mReviews != null) {
            return;
        }
        mRepo = BathroomRepository.getInstance();
        mReviews = mRepo.getMyReviews(id);

        // Log.i("Help", "Me");

    }

    public LiveData<List<Review>> getMyReviews()
    {
        return mReviews;
    }

    public void removeReview(int position)
    {
        List<Review> currentList = mReviews.getValue();
        Log.i("remove", "Removing");
        mRepo.removeReviews(currentList.get(position).getId());
        currentList.remove(position);
        mReviews.setValue(currentList);
    }


    public void resetReview()
    {
        List<Review> currentList = mReviews.getValue();

        mReviews.setValue(currentList);

    }
}


