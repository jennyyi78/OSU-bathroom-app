package com.example.osu_bathroom_app.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class BathroomListViewModel extends ViewModel
{
    private MutableLiveData<List<Bathroom>> mBathrooms;
    private BathroomRepository mRepo;

    public void init()
    {
        if (mBathrooms != null) {
            return;
        }
        mRepo = BathroomRepository.getInstance();
        mBathrooms = mRepo.getBathrooms();
        Log.i("Help", "Me");

    }

    public LiveData<List<Bathroom>> getBathrooms()
    {
        return mBathrooms;
    }


    public void addBathroom(final Bathroom bathroom)
    {
        List<Bathroom> currentList = mBathrooms.getValue();
        currentList.add(bathroom);
        mBathrooms.postValue(currentList);
    }

    public void resetBathroom()
    {
        List<Bathroom> currentList = mBathrooms.getValue();

        mBathrooms.postValue(null);
    }
}