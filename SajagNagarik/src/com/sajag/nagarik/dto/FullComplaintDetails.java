package com.sajag.nagarik.dto;

public class FullComplaintDetails {
	private Long userMobileNo;
	private ComplaintsDetails complaintsDetails;
	/**
	 * @return the userMobileNo
	 */
	public Long getUserMobileNo() {
		return userMobileNo;
	}
	/**
	 * @param userMobileNo the userMobileNo to set
	 */
	public void setUserMobileNo(Long userMobileNo) {
		this.userMobileNo = userMobileNo;
	}
	/**
	 * @return the complaintsDetails
	 */
	public ComplaintsDetails getComplaintsDetails() {
		return complaintsDetails;
	}
	/**
	 * @param complaintsDetails the complaintsDetails to set
	 */
	public void setComplaintsDetails(ComplaintsDetails complaintsDetails) {
		this.complaintsDetails = complaintsDetails;
	}
}
