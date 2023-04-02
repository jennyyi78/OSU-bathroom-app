package com.example.osu_bathroom_app.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.osu_bathroom_app.model.Bathroom;
import com.example.osu_bathroom_app.repository.BathroomRepository;
import com.example.osu_bathroom_app.model.Review;

import java.util.List;

public class ReviewListViewModel extends ViewModel {

    private MutableLiveData<List<Review>> mReviews;

    private BathroomRepository mRepo;


    public void init(long id)
    {
        if (mReviews != null) {
            return;
        }
        mRepo = BathroomRepository.getInstance();
        mReviews = mRepo.getReviews(id);

       // Log.i("Help", "Me");

    }
    public LiveData<List<Review>> getReviews()
    {
        return mReviews;
    }



    public void resetReview()
    {
        List<Review> currentList = mReviews.getValue();

        mReviews.setValue(currentList);

    }
}
