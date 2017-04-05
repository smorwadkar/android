package com.shiv.seismic.detailedquake;

import com.google.gson.annotations.SerializedName;

public class NearByCitiesContents {

	@SerializedName("nearby-cities.inc.html")
	NearByCitiesHtml nearByCitiesHtml;
	
	@SerializedName("nearby-cities.json")
	NearByCitiesJson nearBCitiesJson;
	public NearByCitiesHtml getNearByCitiesHtml() {
		return nearByCitiesHtml;
	}
	public void setNearByCitiesHtml(NearByCitiesHtml nearByCitiesHtml) {
		this.nearByCitiesHtml = nearByCitiesHtml;
	}
	public NearByCitiesJson getNearBCitiesJson() {
		return nearBCitiesJson;
	}
	public void setNearBCitiesJson(NearByCitiesJson nearBCitiesJson) {
		this.nearBCitiesJson = nearBCitiesJson;
	}
	
}
