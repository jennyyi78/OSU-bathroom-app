package com.example.osu_bathroom_app.view_model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.osu_bathroom_app.model.Bathroom;
import com.example.osu_bathroom_app.model.Review;
import com.example.osu_bathroom_app.repository.BathroomRepository;

import java.util.List;

public class FavoriteListViewModel extends ViewModel{




        private MutableLiveData<List<Bathroom>> mBathrooms;

        private BathroomRepository mRepo;


        public void init(long id)
        {
            if (mBathrooms != null) {
                return;
            }
            mRepo = BathroomRepository.getInstance();
            mBathrooms = mRepo.getMyFavorites(id);



        }
        public LiveData<List<Bathroom>> getMyFavorites()
        {
            return mBathrooms;
        }



    public void resetBathroom()
    {
        List<Bathroom> currentList = mBathrooms.getValue();

        mBathrooms.setValue(currentList);

    }


    }


