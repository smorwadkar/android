package com.shiv.seismic.detailedquake;

public class Geoserve
{
    private String id;

    private String indexid;

    private String source;

    private String indexTime;


    private String status;

    private String updateTime;

    private String preferredWeight;

    private GeoserveProperties properties;
    
    private GeoserveContent contents;

    private String code;

    private String type;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getIndexid ()
    {
        return indexid;
    }

    public void setIndexid (String indexid)
    {
        this.indexid = indexid;
    }

    public String getSource ()
    {
        return source;
    }

    public void setSource (String source)
    {
        this.source = source;
    }

    public String getIndexTime ()
    {
        return indexTime;
    }

    public void setIndexTime (String indexTime)
    {
        this.indexTime = indexTime;
    }

    public GeoserveContent getContents ()
    {
        return contents;
    }

    public void setContents (GeoserveContent contents)
    {
        this.contents = contents;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getUpdateTime ()
    {
        return updateTime;
    }

    public void setUpdateTime (String updateTime)
    {
        this.updateTime = updateTime;
    }

    public String getPreferredWeight ()
    {
        return preferredWeight;
    }

    public void setPreferredWeight (String preferredWeight)
    {
        this.preferredWeight = preferredWeight;
    }

    public GeoserveProperties getProperties ()
    {
        return properties;
    }

    public void setProperties (GeoserveProperties properties)
    {
        this.properties = properties;
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", indexid = "+indexid+", source = "+source+", indexTime = "+indexTime+", contents = "+contents+", status = "+status+", updateTime = "+updateTime+", preferredWeight = "+preferredWeight+", properties = "+properties+", code = "+code+", type = "+type+"]";
    }
}
			
		
