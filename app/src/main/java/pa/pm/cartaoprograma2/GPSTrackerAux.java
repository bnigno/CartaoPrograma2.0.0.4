package pa.pm.cartaoprograma2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import pa.pm.localdb.ConfigDataBaseHelper;

public class GPSTrackerAux extends Service implements LocationListener {

    private final Context mContext;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    Location locationGps;
    double latitude; // latitude
    double longitude; // longitude
    private LatLng latlng = null;

    PolylineOptions lineOptions = null;
    ArrayList<LatLng> points = new ArrayList<LatLng>();
    Marker marker = null;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 1 metro
    // modificado
    // para
    // testes

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 60000; // 1 minuto

    // Declaring a Location Manager
    protected LocationManager locationManager;

    static ConfigDataBaseHelper dbConfig;

    public GPSTrackerAux(Context context) {
        this.mContext = context;
        getLocation();
    }

    public LatLng getLocation() {

        try {

            System.out.println("RIDE MARKER: 1");
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {

                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("location", "GPS Enabled");
                    if (locationManager != null) {
                        locationGps = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (locationGps != null) {
                            latlng = new LatLng(location.getLatitude(), location.getLongitude());
                        }
                    }

                }

                else if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("location", "Network");

                    location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        latlng = new LatLng(location.getLatitude(), location.getLongitude());
                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return latlng;
    }

    /**
     * Stop using GPS listener Calling this function will stop using GPS in your
     * app
     * */
    public void stopUsingGPS() {

        if (locationManager != null)
            locationManager.removeUpdates(GPSTrackerAux.this);
    }

    /**
     * Function to get latitude
     * */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     * */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    public double getAcuracy() {
        double acuracy = 9999;
        if (location != null) {
            acuracy = location.getAccuracy();
        }

        // return longitude
        return acuracy;
    }

    public boolean getStatusGPS() {
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isGPSEnabled;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        //System.out.println(" onLocationChanged");
        if (this.location != null && location != null) {
            if (location.getAccuracy() < this.location.getAccuracy()) {
                this.location = location;
            }
        } else {
            this.location = location;
        }

        if(location!=null) {

            points.add(new LatLng(getLocation().latitude, getLocation().longitude));

            int ultimoLatLng = points.size()-1;

            if (marker != null) {
                marker.remove();
            }
            marker = MainActivity.myMap.addMarker(new MarkerOptions()
                    .position(points.get(ultimoLatLng))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pmpa)));

            MainActivity.dbConfig.addRide(LoginActivity.idCard, getLocation().latitude, getLocation().longitude);

            System.out.println("RIDE MARKER: "+ LoginActivity.idCard +" ="+ getLocation().latitude +", "+ getLocation().longitude);

            lineOptions = new PolylineOptions().addAll(points)
                    .color(Color.parseColor(MainActivity.dbConfig.getColorConfig("colorlocalizacaoatual")));
            MainActivity.myMap.addPolyline(lineOptions);
            
        }


    }

    @Override
    public void onProviderDisabled(String provider) {
//		System.out.println(provider + " onProviderDisabled");
    }

    @Override
    public void onProviderEnabled(String provider) {
//		System.out.println(provider + " onProviderDisabled");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
//		System.out.println(provider + " onStatusChanged");
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}
