package com.shiv.seismic.detailedquake;

import com.google.gson.annotations.SerializedName;

	public class ContentsCap
	{
		@SerializedName("contents.xml")
	    private ContentXml contentsXml;

	    @SerializedName("capalert.xml")
	    private CapAlertXml capAlertXml;


	    
	    
	    public ContentXml getContentsXml() {
			return contentsXml;
		}




		public void setContentsXml(ContentXml contentsXml) {
			this.contentsXml = contentsXml;
		}




		public CapAlertXml getCapAlertXml() {
			return capAlertXml;
		}




		public void setCapAlertXml(CapAlertXml capAlertXml) {
			this.capAlertXml = capAlertXml;
		}




		@Override
	    public String toString()
	    {
	        return "ClassPojo [contents.xml = "+contentsXml+", capalert.xml = "+capAlertXml+"]";
	    }
	}
				
				
