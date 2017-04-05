package com.shiv.seismic.detailedquake;

import com.google.gson.annotations.SerializedName;

public class GeoserveContent {

	@SerializedName("geoserve.json")
	GeoserveJson geoserveJson;

	public GeoserveJson getGeoserveJson() {
		return geoserveJson;
	}

	public void setGeoserveJson(GeoserveJson geoserveJson) {
		this.geoserveJson = geoserveJson;
	}
	
}
