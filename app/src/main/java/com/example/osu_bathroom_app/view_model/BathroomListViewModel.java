package com.example.osu_bathroom_app.view_model;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.osu_bathroom_app.model.Bathroom;
import com.example.osu_bathroom_app.repository.BathroomRepository;

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
        mBathrooms.setValue(currentList);
    }

    public void removeBathroom(int position)
    {
        List<Bathroom> currentList = mBathrooms.getValue();

        mRepo.removeBathrooms(currentList.get(position).getId());
        currentList.remove(position);
        mBathrooms.setValue(currentList);
    }

    public void resetBathroom()
    {
        Log.i("Reset", "test");
        List<Bathroom> currentList = mBathrooms.getValue();

        mBathrooms.setValue(currentList);

    }


}