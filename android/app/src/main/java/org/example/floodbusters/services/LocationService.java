package org.example.floodbusters.services;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.IntentSender;
import android.location.Location;
import android.util.Log;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ResolvableApiException;
import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.LocationServices;

public class LocationService {

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Activity contextActivity;

    public LocationService(Activity contextActivity) {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(contextActivity);
        this.contextActivity = contextActivity;
    }

    public void getLastLocation() {
        try {
            Task<Location> lastLocation = mFusedLocationProviderClient.getLastLocation();
            lastLocation.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location == null) {
                        Log.i(TAG, "getLastLocation onSuccess location is null");
                        return;
                    }
                    Log.i(TAG, "getLastLocation onSuccess location[Longitude,Latitude]:"
                            + location.getLongitude() + "," + location.getLatitude());
                    return;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    if (e instanceof ResolvableApiException) {
                        ResolvableApiException apiException = (ResolvableApiException) e;
                        Log.e(TAG, "getLastLocation onFailure:" + apiException.getStatusCode());
                        try {
                            apiException.startResolutionForResult(contextActivity, 2009);
                        } catch (IntentSender.SendIntentException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        Log.e(TAG, "getLastLocation onFailure:" + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "getLastLocation exception:" + e.getMessage());
        }
    }
}
