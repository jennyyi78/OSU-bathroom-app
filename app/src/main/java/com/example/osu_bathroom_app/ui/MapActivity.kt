package com.example.osu_bathroom_app.ui;

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.osu_bathroom_app.R
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.gestures.addOnMapClickListener

class MapActivity : AppCompatActivity() {
    private val TAG = "MapActivity"
    private var mapView: MapView? = null
    private lateinit var mapboxMap: MapboxMap
    private lateinit var pointAnnotationManager: PointAnnotationManager
    private val viewAnnotationViews = mutableListOf<View>()

    //TODO - would use geocoding to connect firebase data to the mapview in this activity
    private val pointLocations = listOf(Pair(40.002370, -83.015170), Pair(40.0016851, -83.0159304), Pair(40.003152, -83.0148552))

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
                        for (p in pointLocations) {
                            addAnnotationToMap(p.first, p.second)
                        }

                    }
                }
        )
//        mapView?.getMapboxMap()?.addOnMapClickListener { point ->
//
//            true
//        }

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
            pointAnnotationManager.addClickListener{
                pointAnn ->
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

    private fun selectLocation(pointAnn: PointAnnotation) {
        Log.i(TAG, "Point: " + pointAnn.point.coordinates().toString() +  pointAnn.isSelected.toString())
        Toast.makeText(getApplicationContext(), "you selected this map", Toast.LENGTH_SHORT).show()
    }

}