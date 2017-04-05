package com.shiv.seismic.quakes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Quakes
{
    private String[] bbox;

    private Features[] features;

    private String type;

    private Metadata metadata;

    public String[] getBbox ()
    {
        return bbox;
    }

    public void setBbox (String[] bbox)
    {
        this.bbox = bbox;
    }

    public Features[] getFeatures ()
    {
        return features;
    }

    public void setFeatures (Features[] features)
    {
        this.features = features;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public Metadata getMetadata ()
    {
        return metadata;
    }

    public void setMetadata (Metadata metadata)
    {
        this.metadata = metadata;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [bbox = "+bbox+", features = "+features+", type = "+type+", metadata = "+metadata+"]";
    }
    
    
}
			
			