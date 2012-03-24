package com.sfm.tour;

import com.google.android.maps.MapActivity;

import android.app.Activity; 
import android.content.Intent; 
import android.graphics.drawable.Drawable;
import android.net.Uri; 
import android.os.Bundle; 


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;



import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class SFMapActivity extends MapActivity {
    
    private MapView mapView;
    ArrayList<GeoPoint> pointsList;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        
        GeoPoint lastPoint = null;
        pointsList = new ArrayList<GeoPoint>();
        mapView = (MapView) findViewById(R.id.mapview);       
        mapView.setBuiltInZoomControls(true);
        
        List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.icon);
        
        for (LocationData l : SfmtourActivity.selectedMovie.getLocations()) {
        	
        CustomItemizedOverlay itemizedOverlay = 
             new CustomItemizedOverlay(drawable, this);
        int lat = (int) (l.getLatitude() * 1E6), lon = (int) (l.getLongitude() * 1E6);
        GeoPoint point = new GeoPoint(lat, lon);
        pointsList.add(point);
        lastPoint = point;
        OverlayItem overlayitem = 
             new OverlayItem(point, SfmtourActivity.selectedMovie.getTitle(), l.getDescription());
        
        itemizedOverlay.addOverlay(overlayitem);
        mapOverlays.add(itemizedOverlay);
        }
             
        
       // MapController mapController = mapView.getController();
        
        //mapController.animateTo(lastPoint);
        //mapController.setZoom(30);
        //mapView.setStreetView(true);
        int minLat = Integer.MAX_VALUE;
        int minLong = Integer.MAX_VALUE;
        int maxLat = Integer.MIN_VALUE;
        int maxLong = Integer.MIN_VALUE;

        for (GeoPoint point : pointsList) {
            minLat = Math.min(point.getLatitudeE6(), minLat);
            minLong = Math.min(point.getLongitudeE6(), minLong);
            maxLat = Math.max(point.getLatitudeE6(), maxLat);
            maxLong = Math.max(point.getLongitudeE6(), maxLong);
        }

        MapController controller = mapView.getController();
        controller.zoomToSpan(
                           Math.abs(minLat - maxLat), Math.abs(minLong - maxLong));
        controller.animateTo(new GeoPoint((maxLat + minLat) / 2,
            (maxLong + minLong) / 2));

        
    }

    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
    
    
    
        
    
}

