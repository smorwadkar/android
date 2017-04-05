package com.shiv.seismic.detailedquake;

public class DetailedQuakes
{
    private String id;

    private MainEventProperties properties;

    private String type;

    private Geometry geometry;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public MainEventProperties getProperties ()
    {
        return properties;
    }

    public void setProperties (MainEventProperties properties)
    {
        this.properties = properties;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public Geometry getGeometry ()
    {
        return geometry;
    }

    public void setGeometry (Geometry geometry)
    {
        this.geometry = geometry;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", properties = "+properties+", type = "+type+", geometry = "+geometry+"]";
    }
}
			
		