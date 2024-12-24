package com.example.mypfe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class MapUser extends AppCompatActivity {

    private MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        setContentView(R.layout.activity_map_user);
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);  //render
        map.setBuiltInZoomControls(true);     //Zoomable
        GeoPoint startPoint = new GeoPoint(36.8065,10.1815 );
        IMapController mapController = map.getController();
        mapController.setZoom(10.0);
        mapController.setCenter(startPoint);
        ArrayList<OverlayItem> items = new ArrayList<>();
        OverlayItem home = new OverlayItem("Tunisie Centre ", "voiture de Ghassen", new GeoPoint(37.02100, 10.08627));
        Drawable m = home.getMarker(0);
        items.add(home);
        items.add(new OverlayItem("Tbessa centre ", "voiture de Ghaith", new GeoPoint(35.58677, 7.75717)));
        items.add(new OverlayItem("Sousse centre ", "voiture de Ghanem", new GeoPoint(35.82469, 10.63007)));
        items.add(new OverlayItem("Gafsa Centre ", "voiture de Ghassen", new GeoPoint(34.43156, 8.77486)));
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(getApplicationContext()
                , items, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return true;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        });
        mOverlay.setFocusItemsOnTap(true);
        map.getOverlays().add(mOverlay);



    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();


    }
    @Override
    public void onResume() {
        super.onResume();
        map.onResume();


    }
}