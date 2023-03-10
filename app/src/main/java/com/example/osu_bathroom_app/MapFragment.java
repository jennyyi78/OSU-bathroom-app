package com.example.osu_bathroom_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;


public class MapFragment extends Fragment
{
    MapView mapView;

    public MapFragment()
    {
        // Required empty public constructor
    }

    public static MapFragment newInstance()
    {
        MapFragment fragment = new MapFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = v.findViewById(R.id.mapView);
        if(mapView != null)
        {
            mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
        }

        return v;
    }
}