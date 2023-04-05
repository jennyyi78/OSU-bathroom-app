package com.example.osu_bathroom_app.ui;

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.osu_bathroom_app.R
import com.example.osu_bathroom_app.model.Bathroom
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import java.util.*

class MapActivity : AppCompatActivity() {
    private val TAG = "MapActivity"
    private var mapView: MapView? = null
    private lateinit var mapboxMap: MapboxMap
    private lateinit var pointAnnotationManager: PointAnnotationManager
    private val lowerLeftLatitude: Double = 39.994303126266644
    private val lowerLeftLongitude: Double = -83.04294296594549
    private val upperRightLatitude: Double = 40.01008264838917
    private val upperRightLongitude: Double = -83.00504871849562
    private lateinit var geocodeAddress: Address

//    val geocodeListener = @RequiresApi(33) object : Geocoder.GeocodeListener {
//        override fun onGeocode(addresses: MutableList<Address>) {
//            geocodeAddress = addresses.first()
//        }
//    }

    private val bathroomLocations = mapOf(
            Bathroom(1, "Caldwell Lab", "2024 Neil Ave, Columbus, OH 43210", 1f, "place for ece") to Pair(40.002370, -83.015170),
            Bathroom(2, "Dreese Lab", "2015 Neil Ave, Columbus, OH 43210", 3f, "tall building with a view") to Pair(40.0016851, -83.0159304),
            Bathroom(3, "Bolz Hall", "2036 Neil Ave, Columbus, OH 43210", 2.6f, "calc 2 happens here") to Pair(40.003152, -83.0148552),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        mapView = findViewById(R.id.mapView)

        mapView?.getMapboxMap()?.loadStyleUri(
                Style.MAPBOX_STREETS,
                object : Style.OnStyleLoaded {
                    override fun onStyleLoaded(style: Style) {

                        val annotationApi = mapView?.annotations
                        pointAnnotationManager = annotationApi?.createPointAnnotationManager(mapView!!)!!
                        for (b in bathroomLocations) {
                            val coordinates = b.value
                            //obtainCoordinates(b.key.address)
                            addAnnotationToMap(coordinates.first, coordinates.second)
                        }
                    }
                }
        )
    }

    private fun addAnnotationToMap(annotationLatitude: Double, annotationLongitude: Double) {
        // Create an instance of the Annotation API and get the PointAnnotationManager.
        bitmapFromDrawableRes(
                this@MapActivity,
                R.drawable.red_marker
        )?.let {
            Log.i(TAG, "created a new point!")
            // Set options for the resulting symbol layer.
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
                    // Define a geographic coordinate.
                    .withPoint(Point.fromLngLat(annotationLongitude, annotationLatitude))
                    // Specify the bitmap you assigned to the point annotation
                    // The bitmap will be added to map style automatically.
                    .withIconImage(it)
            // Add the resulting pointAnnotation to the map.
            pointAnnotationManager.create(pointAnnotationOptions)
            pointAnnotationManager.addClickListener { pointAnn ->
                selectLocation(pointAnn)
                true
            }
        }
    }

    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
            convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
            // copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                    drawable.intrinsicWidth, drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }

    //adding the dialogfragment popup when you
    private fun selectLocation(pointAnn: PointAnnotation) {
        val coordinates = pointAnn.point.coordinates()

        for (b in bathroomLocations) {
            var bathroomCoords = b.value
            //Log.i(TAG, "Annotation Coordinates: " + coordinates.toString() + "Bath Coords: " + bathroomCoords.toString() )
            if (bathroomCoords.second == coordinates.first() && bathroomCoords.first == coordinates.last()) {
                showDialog(b.key)
            }
        }
    }

    private fun showDialog(bathroom: Bathroom) {

        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        val prev: Fragment? = supportFragmentManager.findFragmentByTag("dialog")
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)

        var args: Bundle = bundleOf(
                "title" to bathroom.name,
                "address" to bathroom.address,
                "avgRating" to bathroom.avgRating.toString(),
        )
        // Create and show the dialog.
        val newFragment: DialogFragment = MapLocationDialogFragment()
        newFragment.arguments = args
        newFragment.show(ft, MapLocationDialogFragment.TAG)
    }


//    private fun obtainCoordinates(address: String) {
//        val geocode = Geocoder(this, Locale.getDefault())
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            geocode.getFromLocationName(address, 1, lowerLeftLatitude, lowerLeftLongitude, upperRightLatitude, upperRightLongitude, geocodeListener)
//            Log.i(TAG, String.format("From address: %s ---> (%f, %f", address, geocodeAddress.latitude, geocodeAddress.longitude))
//        }
//
//
//    }

}