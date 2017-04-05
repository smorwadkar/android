package com.shiv.seismic.detailedquake;

import com.google.gson.annotations.SerializedName;

public class Products
{
	@SerializedName("phase-data")
	private PhaseData[] phaseData;

    private Cap[] cap;

    private Origin[] origin;

    @SerializedName("tectonic-summary")
    private TectonicSummary[] tectonicSummary;

    @SerializedName("nearby-cities")
    private NearByCities [] nearbyCities;

    private Geoserve[] geoserve;

    public PhaseData[] getPhaseData ()
    {
        return phaseData;
    }

    public void setPhaseData (PhaseData[] phaseData)
    {
        this.phaseData = phaseData;
    }

    public Cap[] getCap ()
    {
        return cap;
    }

    public void setCap (Cap[] cap)
    {
        this.cap = cap;
    }

    public Origin[] getOrigin ()
    {
        return origin;
    }

    public void setOrigin (Origin[] origin)
    {
        this.origin = origin;
    }

    public TectonicSummary[] gettectonicSummary()
    {
        return tectonicSummary;
    }

    public void setTectonicSummary (TectonicSummary[] tectonicSummary)
    {
        this.tectonicSummary = tectonicSummary;
    }

    public NearByCities[] getNearbyCities ()
    {
        return nearbyCities;
    }

    public void setNearByCities (NearByCities[] nearbyCities)
    {
        this.nearbyCities = nearbyCities;
    }

    public Geoserve[] getGeoserve ()
    {
        return geoserve;
    }

    public void setGeoserve (Geoserve[] geoserve)
    {
        this.geoserve = geoserve;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [phase-data = "+phaseData+", cap = "+cap+", origin = "+origin+", tectonic-summary = "+tectonicSummary+", nearby-cities = "+nearbyCities+", geoserve = "+geoserve+"]";
    }
}
			
			
