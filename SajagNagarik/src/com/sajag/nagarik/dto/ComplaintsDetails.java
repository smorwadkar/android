package com.sajag.nagarik.dto;

import java.io.File;
import java.util.List;

public class ComplaintsDetails {
	private Integer complaintId;
	private String complaintStatus;
	private Long creationTime;
	private Long lastUpdateTime;
	private String comments;
	private List<File> images;
	private Integer departmentId;
	
	
	/**
	 * @return the complaintId
	 */
	public Integer getComplaintId() {
		return complaintId;
	}
	/**
	 * @param complaintId the complaintId to set
	 */
	public void setComplaintId(Integer complaintId) {
		this.complaintId = complaintId;
	}
	/**
	 * @return the complaintStatus
	 */
	public String getComplaintStatus() {
		return complaintStatus;
	}
	/**
	 * @param complaintStatus the complaintStatus to set
	 */
	public void setComplaintStatus(String complaintStatus) {
		this.complaintStatus = complaintStatus;
	}

	/**
	 * @return the creationTime
	 */
	public Long getCreationTime() {
		return creationTime;
	}
	/**
	 * @param creationTime the creationTime to set
	 */
	public void setCreationTime(Long creationTime) {
		this.creationTime = creationTime;
	}
	/**
	 * @return the lastUpdateTime
	 */
	public Long getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the images
	 */
	public List<File> getImages() {
		return images;
	}
	/**
	 * @param images the images to set
	 */
	public void setImages(List<File> images) {
		this.images = images;
	}
	/**
	 * @return the departmentId
	 */
	public Integer getDepartmentId() {
		return departmentId;
	}
	/**
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
}
