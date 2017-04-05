package com.shiv.seismic.detailedquake;


	public class EventSourceProperties
	{
	    private String eventsource;

	    private String eventsourcecode;

	    public String getEventsource ()
	    {
	        return eventsource;
	    }

	    public void setEventsource (String eventsource)
	    {
	        this.eventsource = eventsource;
	    }

	    public String getEventsourcecode ()
	    {
	        return eventsourcecode;
	    }

	    public void setEventsourcecode (String eventsourcecode)
	    {
	        this.eventsourcecode = eventsourcecode;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [eventsource = "+eventsource+", eventsourcecode = "+eventsourcecode+"]";
	    }
	}
				
				
