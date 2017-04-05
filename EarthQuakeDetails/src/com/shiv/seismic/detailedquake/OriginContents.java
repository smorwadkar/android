package com.shiv.seismic.detailedquake;

import com.google.gson.annotations.SerializedName;

public class OriginContents {

	@SerializedName("contents.xml")
	ContentXml contentXml;
	
	@SerializedName("quakeml.xml")
	QuakeXml quakeXml;

	public ContentXml getContentXml() {
		return contentXml;
	}

	public void setContentXml(ContentXml contentXml) {
		this.contentXml = contentXml;
	}

	public QuakeXml getQuakeXml() {
		return quakeXml;
	}

	public void setQuakeXml(QuakeXml quakeXml) {
		this.quakeXml = quakeXml;
	}
	
	
}
