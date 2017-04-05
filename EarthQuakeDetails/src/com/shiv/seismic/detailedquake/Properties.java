package com.shiv.seismic.detailedquake;

public class Properties
{
    private String eventsource;

    private String eventsourcecode;

    private String reviewStatus;

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

    
    public String getReviewStatus() {
		return reviewStatus;
	}

	public void setReviewStatus(String reviewStatus) {
		this.reviewStatus = reviewStatus;
	}

	@Override
    public String toString()
    {
        return "ClassPojo [eventsource = "+eventsource+", eventsourcecode = "+eventsourcecode+", review-status = "+reviewStatus+"]";
    }
}
			
			
