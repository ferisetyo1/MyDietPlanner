package feri.com.mydietplanner.Activity;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import feri.com.mydietplanner.Adapter.PenjualMakananAdapter;
import feri.com.mydietplanner.Model.PenjualMakananModel;
import feri.com.mydietplanner.R;

public class MapsMakananActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMarkerClickListener {
    private FragmentActivity fa;
    private GoogleMap nMap;
    private ChildEventListener mChildEventListener;
    FirebaseDatabase database;
    DatabaseReference foodRef;
    PenjualMakananAdapter PMA;
    Marker marker;
    int _long, _lat;
    String _namapenjual;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        fa = new FragmentActivity();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
//        ChildEventListener mChildEventListener;
//        foodRef = FirebaseDatabase.getInstance().getReference("Foods");
//        foodRef.push().setValue(marker);
        //PMA = new PenjualMakananAdapter(this);
        Intent intent = getIntent();
        _namapenjual = intent.getStringExtra("alamat");
        _lat = (int) intent.getIntExtra("lat", 0);
        _long = (int) intent.getIntExtra("long", 0);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        nMap = googleMap;
        nMap.setOnMarkerClickListener(this);
        nMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng loc = new LatLng(_lat, _long);
        nMap.addMarker(new MarkerOptions().position(loc).title(_namapenjual));
        nMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        nMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 13.0f), 5000, null);
//        foodRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot s : dataSnapshot.getChildren()){
//                    PenjualMakananModel info = s.getValue(PenjualMakananModel.class);
//                    LatLng loc = new LatLng(info.getLat(),info.getLong());
//                    nMap.addMarker(new MarkerOptions().position(loc).title(info.getNama()));
//                    nMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
//                    nMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,13.0f),5000,null);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//
//        nMap.addMarker(new MarkerOptions().position(toko1).title("Indomaret Panjaitan Malang"));
//
//        nMap.moveCamera(CameraUpdateFactory.newLatLng(toko1));

//        nMap.animateCamera(CameraUpdateFactory.newLatLngZoom(toko1, 13.0f),5000,null);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
