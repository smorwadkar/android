package com.shiv.seismic.detailedquake;


public class Contents
{
    private TectonicHtml tectonicSummaryIncHtml;

    
    
    public TectonicHtml getTectonicSummaryIncHtml() {
		return tectonicSummaryIncHtml;
	}



	public void setTectonicSummaryIncHtml(TectonicHtml tectonicSummaryIncHtml) {
		this.tectonicSummaryIncHtml = tectonicSummaryIncHtml;
	}



	@Override
    public String toString()
    {
        return "ClassPojo [tectonic-summary.inc.html = "+tectonicSummaryIncHtml+"]";
    }
}