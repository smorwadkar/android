package com.shiv.seismic.detailedquake;

import com.google.gson.annotations.SerializedName;

public class TectonicSummaryContents {

	@SerializedName("tectonic-summary.inc.html")
	TectonicHtml techHtml;

	public TectonicHtml getTechHtml() {
		return techHtml;
	}

	public void setTechHtml(TectonicHtml techHtml) {
		this.techHtml = techHtml;
	}
	
}
