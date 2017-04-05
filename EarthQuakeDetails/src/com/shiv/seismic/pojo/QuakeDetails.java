package com.shiv.seismic.pojo;

import java.io.Serializable;
import java.util.Date;

public class QuakeDetails implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String quakeTitle;
	public String quakeInfo;
	public String longitude;
	public String latitude;
	public String time;
	public String depth;
	public String detailsURL;
	
	
	public String getQuakeTitle() {
		return quakeTitle;
	}
	public void setQuakeTitle(String quakeTitle) {
		this.quakeTitle = quakeTitle;
	}
	public String getQuakeInfo() {
		return quakeInfo;
	}
	public void setQuakeInfo(String quakeInfo) {
		this.quakeInfo = quakeInfo;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public String getDetailsURL() {
		return detailsURL;
	}
	public void setDetailsURL(String detailsURL) {
		this.detailsURL = detailsURL;
	}
	
	
	
}