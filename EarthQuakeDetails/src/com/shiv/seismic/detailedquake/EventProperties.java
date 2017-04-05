package com.shiv.seismic.detailedquake;

import com.google.gson.annotations.SerializedName;

public class EventProperties
{
    private String eventtime;

    @SerializedName("magnitude-type")
    private String magnitudeType;

    @SerializedName("origin-source")
    private String originSource;

    @SerializedName("azimuthal-gap")
    private String azimuthaGap;

    @SerializedName("evaluation-status")
    private String evaluatioStatus;

    private String magnitude;

    private String eventsource;

    private String eventsourcecode;

    @SerializedName("magnitude-source")
    private String magnitudeSource;

    private String depth;

    @SerializedName("magnitude-error")
    private String magnitudeError;

    @SerializedName("horizontal-error")
    private String horizontalError;

    @SerializedName("num-phases-used")
    private String numPhasesUsed;

    @SerializedName("magnitude-num-stations-used")
    private String magnitudeNumStationsUsed;

    @SerializedName("vertical-error")
    private String verticalError;

    @SerializedName("minimum-distance")
    private String minimumDistance;

    private String eventParametersPublicID;

    @SerializedName("quakeml-publicid")
    private String quakemlPublicid;

    private String longitude;

    private String latitude;

    @SerializedName("depth-type")
    private String depthType;

    @SerializedName("event-type")
    private String eventType;

    @SerializedName("standard-error")
    private String standardError;

    @SerializedName("review-status")
    private String reviewStatus;
    
    

	public String getEventtime() {
		return eventtime;
	}

	public void setEventtime(String eventtime) {
		this.eventtime = eventtime;
	}

	public String getMagnitudeType() {
		return magnitudeType;
	}

	public void setMagnitudeType(String magnitudeType) {
		this.magnitudeType = magnitudeType;
	}

	public String getOriginSource() {
		return originSource;
	}

	public void setOriginSource(String originSource) {
		this.originSource = originSource;
	}

	public String getAzimuthaGap() {
		return azimuthaGap;
	}

	public void setAzimuthaGap(String azimuthaGap) {
		this.azimuthaGap = azimuthaGap;
	}

	public String getEvaluatioStatus() {
		return evaluatioStatus;
	}

	public void setEvaluatioStatus(String evaluatioStatus) {
		this.evaluatioStatus = evaluatioStatus;
	}

	public String getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(String magnitude) {
		this.magnitude = magnitude;
	}

	public String getEventsource() {
		return eventsource;
	}

	public void setEventsource(String eventsource) {
		this.eventsource = eventsource;
	}

	public String getEventsourcecode() {
		return eventsourcecode;
	}

	public void setEventsourcecode(String eventsourcecode) {
		this.eventsourcecode = eventsourcecode;
	}

	public String getMagnitudeSource() {
		return magnitudeSource;
	}

	public void setMagnitudeSource(String magnitudeSource) {
		this.magnitudeSource = magnitudeSource;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getMagnitudeError() {
		return magnitudeError;
	}

	public void setMagnitudeError(String magnitudeError) {
		this.magnitudeError = magnitudeError;
	}

	public String getHorizontalError() {
		return horizontalError;
	}

	public void setHorizontalError(String horizontalError) {
		this.horizontalError = horizontalError;
	}

	public String getNumPhasesUsed() {
		return numPhasesUsed;
	}

	public void setNumPhasesUsed(String numPhasesUsed) {
		this.numPhasesUsed = numPhasesUsed;
	}

	public String getMagnitudeNumStationsUsed() {
		return magnitudeNumStationsUsed;
	}

	public void setMagnitudeNumStationsUsed(String magnitudeNumStationsUsed) {
		this.magnitudeNumStationsUsed = magnitudeNumStationsUsed;
	}

	public String getVerticalError() {
		return verticalError;
	}

	public void setVerticalError(String verticalError) {
		this.verticalError = verticalError;
	}

	public String getMinimumDistance() {
		return minimumDistance;
	}

	public void setMinimumDistance(String minimumDistance) {
		this.minimumDistance = minimumDistance;
	}

	public String getEventParametersPublicID() {
		return eventParametersPublicID;
	}

	public void setEventParametersPublicID(String eventParametersPublicID) {
		this.eventParametersPublicID = eventParametersPublicID;
	}

	public String getQuakemlPublicid() {
		return quakemlPublicid;
	}

	public void setQuakemlPublicid(String quakemlPublicid) {
		this.quakemlPublicid = quakemlPublicid;
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

	public String getDepthType() {
		return depthType;
	}

	public void setDepthType(String depthType) {
		this.depthType = depthType;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getStandardError() {
		return standardError;
	}

	public void setStandardError(String standardError) {
		this.standardError = standardError;
	}

	public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

}
			
			
