package com.tny.utils;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Class to implement method to get current geolocation
 * 
 * @author
 * 
 */
public class GeoLocationUtil {

	public static LocationManager locationManager;
	public static LocationListener locationListener;
	public static Context context;
	public static String lastProvider;

	/*
	 * get last known location
	 */
	public static Location getGeoLocation(Context context) {
		GeoLocationUtil.context = context;

		locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		locationListener = new CustomLocationListener();

		String provider = getBestProvider(locationListener);
		if (provider != null) {
			locationManager.requestLocationUpdates(provider, 0, 0,
					locationListener);


			Location location = locationManager.getLastKnownLocation(provider);
			System.out.println("Location======" + location);
			removeLocationListener();
			if (isValidLocation(location)) {
				return location;

			}
		} else {
			Toast
					.makeText(
							context,
							"Could not retrieve your location.Please enable GPS or network locations.",
							Toast.LENGTH_LONG).show();
			return null;
		}
		Toast.makeText(context,
				"Could not retrieve your location.Please try again.",
				Toast.LENGTH_LONG).show();
		return null;
	}

	public static class CustomLocationListener implements LocationListener {
		public long totalDistance;
		public static long count = 0;

		public void onLocationChanged(Location location) {

		}

		public void onProviderDisabled(String provider) {

		}

		public void onProviderEnabled(String provider) {

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

	}

	private static boolean isValidLocation(Location location) {
		if (location != null && location.getLatitude() != 0
				&& location.getLongitude() != 0) {
			return true;
		}
		return false;
	}

	public static void removeLocationListener() {
		if (locationManager != null) {
			locationManager.removeUpdates(locationListener);
			locationManager = null;

		}
	}

	private static String getBestProvider(LocationListener locationListener) {
		String provider;
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, locationListener);
		Location gpsLocation = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (gpsLocation != null
				&& (gpsLocation.getLatitude() != 0 && gpsLocation
						.getLongitude() != 0)) {
			provider = LocationManager.GPS_PROVIDER;
		} else {
			provider = locationManager.getBestProvider(new Criteria(), true);
		}

		return provider;
	}

}
