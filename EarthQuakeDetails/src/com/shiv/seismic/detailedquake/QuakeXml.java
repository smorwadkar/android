package com.shiv.seismic.detailedquake;

public class QuakeXml
{
    private String lastModified;

    private String length;

    private String contentType;

    private String url;

    public String getLastModified ()
    {
        return lastModified;
    }

    public void setLastModified (String lastModified)
    {
        this.lastModified = lastModified;
    }

    public String getLength ()
    {
        return length;
    }

    public void setLength (String length)
    {
        this.length = length;
    }

    public String getContentType ()
    {
        return contentType;
    }

    public void setContentType (String contentType)
    {
        this.contentType = contentType;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lastModified = "+lastModified+", length = "+length+", contentType = "+contentType+", url = "+url+"]";
    }
}
			
			
