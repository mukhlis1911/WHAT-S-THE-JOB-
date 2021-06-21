package in.co.job.portal.bean;

import java.util.Date;
import in.co.job.portal.bean.BaseBean;

public class MakeResumeBean extends BaseBean {

	private String name;
	private String email;
	private String mobile;

	private String skill;

	private Date date;
	private String place;
	private String objective;

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}


	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	
	/*
	 * @Override public String getKey() { // TODO Auto-generated method stub return
	 * null; }
	 * 
	 * @Override public String getValue() { // TODO Auto-generated method stub
	 * return null; }
	 */

	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
