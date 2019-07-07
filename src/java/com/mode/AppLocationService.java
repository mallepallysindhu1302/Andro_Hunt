/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Context
 *  android.content.Intent
 *  android.location.Location
 *  android.location.LocationListener
 *  android.location.LocationManager
 *  android.os.Bundle
 *  android.os.IBinder
 *  java.lang.Object
 *  java.lang.String
 */
package com.mode;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

public class AppLocationService
extends Service
implements LocationListener {
    private static final long MIN_DISTANCE_FOR_UPDATE = 10L;
    private static final long MIN_TIME_FOR_UPDATE = 120000L;
    Location location;
    protected LocationManager locationManager;

    public AppLocationService(Context context) {
        this.locationManager = (LocationManager)context.getSystemService("location");
    }

    public Location getLocation(String string2) {
        if (this.locationManager.isProviderEnabled(string2)) {
            this.locationManager.requestLocationUpdates(string2, 120000L, 10.0f, (LocationListener)this);
            if (this.locationManager != null) {
                this.location = this.locationManager.getLastKnownLocation(string2);
                return this.location;
            }
        }
        return null;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onLocationChanged(Location location) {
    }

    public void onProviderDisabled(String string2) {
    }

    public void onProviderEnabled(String string2) {
    }

    public void onStatusChanged(String string2, int n, Bundle bundle) {
    }
}

