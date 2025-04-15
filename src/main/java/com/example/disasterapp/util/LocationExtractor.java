package com.example.disasterapp.util;

import java.io.File;
import java.io.IOException;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;

public class LocationExtractor {

	public static class Location {
		public final double latitude;
		public final double longitude;

		public Location(double latitude, double longitude) {
			this.latitude = latitude;
			this.longitude = longitude;
		}
	}

	public static Location extractLocation(File imageFile) {
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
			GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);

			if (gpsDirectory != null && gpsDirectory.containsTag(GpsDirectory.TAG_LATITUDE)
					&& gpsDirectory.containsTag(GpsDirectory.TAG_LONGITUDE)) {
				GeoLocation geoLocation = gpsDirectory.getGeoLocation();
				if (geoLocation != null && !geoLocation.isZero()) { // 0,0でないことを確認
					return new Location(geoLocation.getLatitude(), geoLocation.getLongitude());
				}
			}
		} catch (ImageProcessingException | IOException e) {
			System.err.println("Error reading image metadata: " + e.getMessage());
			// エラーログを出力するなど
		}
		return null; // 位置情報が見つからないか、エラーが発生した場合
	}
}