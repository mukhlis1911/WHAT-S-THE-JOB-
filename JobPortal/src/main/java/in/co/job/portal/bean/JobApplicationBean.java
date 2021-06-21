package in.co.job.portal.bean;

import java.sql.Timestamp;
import java.util.Date;

public class JobApplicationBean extends BaseBean {

	private long recruiterId;
	private long jobId;
	private long userId;
	private Timestamp applyDate;
	private String userName;
	private String resumeFile;
	private String processed;

	public String getResumeFile() {
		return resumeFile;
	}

	public void setResumeFile(String resumeFile) {
		this.resumeFile = resumeFile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getRecruiterId() {
		return recruiterId;
	}

	public void setRecruiterId(long recruiterId) {
		this.recruiterId = recruiterId;
	}

	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getKey() {
		return null;
	}

	public String getValue() {
		return null;
	}

	public Timestamp getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Timestamp applyDate) {
		this.applyDate = applyDate;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}
	
	@Override
	public String toString() {
		return "JobApplicationBean [recruiterId=" + recruiterId + ", jobId=" + jobId + ", userId=" + userId
				+ ", applyDate=" + applyDate + ", userName=" + userName + ", resumeFile=" + resumeFile + ", processed="
				+ processed + "]";
	}

}
